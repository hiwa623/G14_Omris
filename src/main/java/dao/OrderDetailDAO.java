package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.dto.CartItemDTO;

public class OrderDetailDAO {
	// SQL文の定義
    private static final String INSERT_DETAIL_SQL = 
        "INSERT INTO order_details (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
    
    /**
     * 注文明細を登録する。
     * Service側でトランザクションを制御するため、Connectionを受け取る。
     * * @param conn データベース接続
     * @param orderId 親となる注文のID
     * @param item カート内の1つのアイテム
     * @throws SQLException データベースエラー
     */
    public void insertOrderDetail(Connection conn, int orderId, CartItemDTO item) throws SQLException {
        
        try (PreparedStatement ps = conn.prepareStatement(INSERT_DETAIL_SQL)) {
            // パラメータのセット
            ps.setInt(1, orderId);
            ps.setInt(2, item.getProduct().getProductId());
            ps.setInt(3, item.getQuantity());
            ps.setInt(4, item.getProduct().getPrice());

            // 実行
            int result = ps.executeUpdate();
            
            if (result == 0) {
                throw new SQLException("明細の登録に失敗しました。");
            }
        }
    }
}
