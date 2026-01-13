package model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.DBManager;
import dao.OrderDAO;
import dao.OrderDetailDAO;
import model.dto.CartItemDTO;

/**
 * OrderService
 * 注文処理の業務ロジック（トランザクション管理）を担当
 */
public class OrderService {
	private OrderDAO orderDAO = new OrderDAO();
    private OrderDetailDAO detailDAO = new OrderDetailDAO();

    /**
     * 注文確定処理（一連の登録を一つのトランザクションとして実行）
     * @param cart カート内の商品リスト
     * @return 処理の成否
     */
    public boolean checkout(List<CartItemDTO> cart) {
        if (cart == null || cart.isEmpty()) return false;

        Connection conn = null;
        try {
            conn = DBManager.getConnection();
            // 1. 自動コミットをオフ（トランザクション開始）
            conn.setAutoCommit(false);

            // 合計金額の計算（業務ロジック）
            int total = cart.stream()
                            .mapToInt(i -> i.getProduct().getPrice() * i.getQuantity())
                            .sum();

            // 2. ordersテーブルへ挿入し、発行されたIDを取得
            int orderId = orderDAO.insertOrder(conn, total);
            
            if (orderId == 0) {
                throw new SQLException("注文IDの取得に失敗しました。");
            }

            // 3. 全てのカートアイテムを order_details テーブルへ挿入
            for (CartItemDTO item : cart) {
                detailDAO.insertOrderDetail(conn, orderId, item);
            }

            // 4. 全て成功したら確定
            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            // 5. どこかでエラーが起きたら全て元に戻す
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
