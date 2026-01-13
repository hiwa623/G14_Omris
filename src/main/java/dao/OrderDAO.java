package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderDAO {
    
    private static final String INSERT_ORDER_SQL = 
        "INSERT INTO orders (order_date, total_price) VALUES (NOW(), ?)";

    public int insertOrder(Connection conn, int totalPrice) throws SQLException {
        // RETURN_GENERATED_KEYS を指定して、自動採番された ID を取得可能にする
        try (PreparedStatement ps = conn.prepareStatement(INSERT_ORDER_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, totalPrice);
            ps.executeUpdate();

            // 発行された order_id を取得
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    return 0;
                }
            }
        }
    }
}