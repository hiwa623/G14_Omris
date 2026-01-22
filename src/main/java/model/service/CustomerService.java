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

    /**
     * 指定されたIDの商品情報を取得する
     */
    public ProductDTO getProductById(int productId) {
        return productDAO.findById(productId);
    }

    /**
     * 注文確定処理（トランザクション制御）
     * @param cartItems カートの中身
     * @param tableId テーブル番号
     * @param customerCount 客数
     * @return 注文ID (成功時)
     * @throws Exception
     */
    // 【修正】引数に tableId と customerCount を追加しました
    public int placeOrder(List<CartItemDTO> cartItems, int tableId, int customerCount) throws Exception {
        Connection conn = null;
        try {
            conn = DBManager.getConnection();
            conn.setAutoCommit(false); // トランザクション開始

            // 1. 注文(Orders)テーブルへの登録
            // 合計金額を計算
            int totalAmount = calculateTotal(cartItems);
            
            // 【修正】OrderDAOに人数などを渡す必要がある場合はここで渡す
            // 今回は引数で受け取った tableId と 計算した totalAmount を渡す
            int orderId = orderDAO.insertOrder(conn, tableId, totalAmount);

            // 2. 注文明細(OrderDetails)とオプションの登録
            for (CartItemDTO item : cartItems) {
                // 明細登録
                int detailId = orderDetailDAO.insertOrderDetail(conn, orderId, item);
                
                // オプション登録
                // 【修正】getOptionIds() ではなく getOptionList() を使用
                if (item.getOptionList() != null) {
                    for (OptionDTO opt : item.getOptionList()) {
                        // OptionDTO から ID を取り出して登録
                        // ※OptionDTOのID取得メソッドが getId() である前提です
                        orderDAO.insertSpecifiedOption(conn, detailId, opt.getId());
                    }
                }
            }

            conn.commit(); // コミット
            return orderId;

        } catch (Exception e) {
            if (conn != null) conn.rollback(); // 失敗したらロールバック
            throw e;
        } finally {
            if (conn != null) conn.close();
        }
    }
    
    /**
     * 
     * @param tableId
     * @return
     */
    public List<OrderHistoryDTO> getOrderHistory(int tableId) {
        return orderDAO.findHistoryByTableId(tableId);
    }
    
    // 合計金額計算用
    private int calculateTotal(List<CartItemDTO> items) {
        int total = 0;
        for (CartItemDTO item : items) {
            // 商品の小計
            int subTotal = item.getProduct().getPrice() * item.getQuantity();
            
            // 【追加】オプション料金の加算
            if (item.getOptionList() != null) {
                for (OptionDTO opt : item.getOptionList()) {
                    // オプション単価 * 数量
                    subTotal += opt.getOptionPrice() * item.getQuantity();
                }
            }
            total += subTotal;
        }
        return total;
    }
}