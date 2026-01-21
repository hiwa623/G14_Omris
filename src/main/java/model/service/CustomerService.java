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
import model.dto.ProductDTO;

public class CustomerService {

    // 各DAOのインスタンスを持っておく
    private CategoryDAO categoryDAO = new CategoryDAO();
    private ProductDAO productDAO = new ProductDAO();
    private OptionDAO optionDAO = new OptionDAO();
    private OrderDAO orderDAO = new OrderDAO();
    private OrderDetailDAO orderDetailDAO = new OrderDetailDAO();

    /**
     * メニュー画面に必要な情報をまとめて取得する
     * 今後 ViewModel を返すように変更しても良い
     */
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
     * 注文確定処理（トランザクション制御）
     * @param cartItems カートの中身
     * @return 注文ID (成功時)
     * @throws Exception
     */
    public int placeOrder(List<CartItemDTO> cartItems) throws Exception {
        Connection conn = null;
        try {
            conn = DBManager.getConnection();
            conn.setAutoCommit(false); // トランザクション開始

            // 1. 注文(Orders)テーブルへの登録
            // ★重要: 客数とテーブル番号が未実装のため、仮の値を入れます
            int tempTableId = 1;     // 仮: テーブル1番
            int tempCustomerCount = 2; // 仮: 2名
            
            // OrderDAOのinsertOrderを少し修正して、IDを返すようにする必要があります
            // 現状のOrderDAOに合わせて、まずは注文枠を作ります
            int orderId = orderDAO.insertOrder(conn, tempTableId, calculateTotal(cartItems));

            // 2. 注文明細(OrderDetails)とオプションの登録
            for (CartItemDTO item : cartItems) {
                // 明細登録
                int detailId = orderDetailDAO.insertOrderDetail(conn, orderId, item);
                
                // オプション登録 (CartItemDTOにオプションリストがあると仮定)
                if (item.getOptionIds() != null) {
                    for (int optionId : item.getOptionIds()) {
                        orderDAO.insertSpecifiedOption(conn, detailId, optionId);
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
    
    // 合計金額計算用
    private int calculateTotal(List<CartItemDTO> items) {
        int total = 0;
        for (CartItemDTO item : items) {
            int subTotal = item.getProduct().getPrice();
            // ※ここでオプション料金の加算ロジックも必要になりますが、
            // まずは商品単価×個数で計算します
            total += subTotal * item.getQuantity();
        }
        return total;
    }
    
    /**
     * 指定されたIDの商品情報を取得する
     */
    public ProductDTO getProductById(int productId) {
        return productDAO.findById(productId);
    }
}
