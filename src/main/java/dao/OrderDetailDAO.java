package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet; // 追加
import java.sql.SQLException;

import model.dto.CartItemDTO;

public class OrderDetailDAO {
    // 【修正】新設計に合わせて product_status_id（初期値 'NEW'）を追加
    private static final String INSERT_DETAIL_SQL = 
        "INSERT INTO order_details (order_id, product_id, product_status_id, quantity, price) " +
        "VALUES (?, ?, 'COOKING', ?, ?)";
    
    /**
     * 注文明細を登録し、生成されたIDを返す。
     */
    public int insertOrderDetail(Connection conn, int orderId, CartItemDTO item) throws SQLException {
        // 【重要】生成された detail_id を取得するためにカラム名を指定
        String[] generatedColumns = {"detail_id"}; 
        
        try (PreparedStatement ps = conn.prepareStatement(INSERT_DETAIL_SQL, generatedColumns)) {
            ps.setInt(1, orderId);
            ps.setInt(2, item.getProduct().getProductId());
            ps.setInt(3, item.getQuantity());
            ps.setInt(4, item.getProduct().getPrice());

            int result = ps.executeUpdate();
            
            if (result == 0) {
                throw new SQLException("明細の登録に失敗しました。");
            }

            // --- 【追加】生成された detail_id を取得して返す ---
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("明細IDの取得に失敗しました。");
                }
            }
        }
    }
}