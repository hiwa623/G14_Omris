package model.service;

import java.sql.Connection;
import java.util.List;

import dao.CategoryDAO;
import dao.DBManager;
import dao.OptionDAO;
import dao.OrderDAO;
import dao.OrderDetailDAO;
import dao.ProductDAO;
import model.dto.CartItemDTO;
import model.dto.CategoryDTO;
import model.dto.OptionDTO;
import model.dto.OrderHistoryDTO;
import model.dto.ProductDTO;

public class CustomerService {

    private CategoryDAO categoryDAO = new CategoryDAO();
    private ProductDAO productDAO = new ProductDAO();
    private OptionDAO optionDAO = new OptionDAO();
    private OrderDAO orderDAO = new OrderDAO();
    private OrderDetailDAO orderDetailDAO = new OrderDetailDAO();

    public List<CategoryDTO> getCategoryList() {
        return categoryDAO.findAll();
    }

    public List<ProductDTO> getProductList() {
        return productDAO.findAll();
    }

    public List<OptionDTO> getOptionList() {
        return optionDAO.findAll();
    }

    public ProductDTO getProductById(int productId) {
        return productDAO.findById(productId);
    }

    /**
     * 注文確定処理（トランザクション制御）
     * オプション料金込みで合計金額を計算して登録します。
     */
    public int placeOrder(List<CartItemDTO> cartItems, int tableId, int customerCount) throws Exception {
        Connection conn = null;
        try {
            conn = DBManager.getConnection();
            conn.setAutoCommit(false); // トランザクション開始

            // 1. 合計金額を計算（★ここでオプション込みの計算メソッドを呼ぶ！）
            int totalAmount = calculateTotal(cartItems);

            // 2. 注文テーブル(orders)に登録
            int orderId = orderDAO.insertOrder(conn, tableId, totalAmount);

            // 3. 注文明細(order_details)とオプション詳細を登録
            for (CartItemDTO item : cartItems) {
                // 明細登録 (OrderDetailDAOの実装に合わせています)
                int detailId = orderDetailDAO.insertOrderDetail(conn, orderId, item);
                
                // オプション登録
                if (item.getOptionList() != null) {
                    for (OptionDTO opt : item.getOptionList()) {
                        orderDAO.insertSpecifiedOption(conn, detailId, opt.getId());
                    }
                }
            }

            conn.commit(); // 確定
            return orderId;

        } catch (Exception e) {
            if (conn != null) conn.rollback(); // 失敗したら戻す
            throw e;
        } finally {
            if (conn != null) conn.close();
        }
    }
    
    public List<OrderHistoryDTO> getOrderHistory(int tableId) {
        return orderDAO.findHistoryByTableId(tableId);
    }
    
    /**
     * 合計金額計算用メソッド
     * （商品単価 ＋ オプション単価） × 数量 で計算します
     */
    private int calculateTotal(List<CartItemDTO> items) {
        int total = 0;
        
        if (items == null) return 0;

        for (CartItemDTO item : items) {
            // ベース価格（商品単価 * 数量）
            int lineTotal = item.getProduct().getPrice() * item.getQuantity();
            
            // オプション加算
            if (item.getOptionList() != null) {
                for (OptionDTO opt : item.getOptionList()) {
                    // オプション単価 * 数量 を加算
                    lineTotal += opt.getOptionPrice() * item.getQuantity();
                }
            }
            total += lineTotal;
        }
        return total;
    }
}