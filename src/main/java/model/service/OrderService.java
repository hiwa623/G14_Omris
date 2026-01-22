package model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.DBManager;
import dao.OrderDAO;
import dao.OrderDetailDAO;
import model.dto.CartItemDTO;
import model.dto.OptionDTO; // 追加
import model.dto.OrderHistoryDTO;

public class OrderService {
    private OrderDAO orderDAO = new OrderDAO();
    private OrderDetailDAO detailDAO = new OrderDetailDAO();

    // startNewOrder は変更なし（省略可能ですがそのままにしておきます）
    public boolean startNewOrder(int tableId, int customerCount) {
        if (customerCount < 1) return false;
        Connection conn = null;
        try {
            conn = DBManager.getConnection();
            int orderId = orderDAO.insertOrder(conn, tableId, 0); 
            if (orderId > 0) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }
    
    /**
     * 注文確定処理
     */
    public boolean checkout(List<CartItemDTO> cart, int tableId) {
        if (cart == null || cart.isEmpty()) return false;

        Connection conn = null;
        try {
            conn = DBManager.getConnection();
            conn.setAutoCommit(false);

            // 合計金額の計算（ここもオプション料金を含めるべきですが、簡易修正にとどめます）
            int total = 0;
            for(CartItemDTO item : cart) {
                int lineTotal = item.getProduct().getPrice() * item.getQuantity();
                // オプション料金計算を追加するならここに記述
                if(item.getOptionList() != null){
                     for(OptionDTO opt : item.getOptionList()){
                         lineTotal += opt.getOptionPrice() * item.getQuantity();
                     }
                }
                total += lineTotal;
            }

            // 注文(親)
            int orderId = orderDAO.insertOrder(conn, tableId, total);
            if (orderId == 0) throw new SQLException("注文IDの取得に失敗しました。");

            // 明細(子)
            for (CartItemDTO item : cart) {
                int detailId = detailDAO.insertOrderDetail(conn, orderId, item);
                if (detailId == 0) throw new SQLException("明細IDの取得に失敗しました。");

                // 【修正】オプション登録
                if (item.getOptionList() != null && !item.getOptionList().isEmpty()) {
                    for (OptionDTO opt : item.getOptionList()) {
                        // OptionDTO から ID を取得
                        orderDAO.insertSpecifiedOption(conn, detailId, opt.getId());
                    }
                }
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (SQLException se) { se.printStackTrace(); }
            return false;
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
    
    public List<OrderHistoryDTO> getOrderHistory() {
        return orderDAO.findAllOrders();
    }
}