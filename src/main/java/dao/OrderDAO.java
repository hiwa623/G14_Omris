package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dto.OrderDetailDTO;
import model.dto.OrderHistoryDTO;

public class OrderDAO {
//	MySQL形式
//    private static final String INSERT_ORDER_SQL = 
//        "INSERT INTO orders (order_date, total_price) VALUES (NOW(), ?)";
    
 // Oracle形式
	private static final String INSERT_ORDER_SQL = 
		    "INSERT INTO orders (order_date, total_price) VALUES (SYSDATE, ?)";

 // 注文一覧を取得（新しい順）
    private static final String SELECT_ALL_ORDERS_SQL = 
        "SELECT order_id, order_date, total_price FROM orders ORDER BY order_date DESC";

    // 特定の注文に紐づく明細を取得（商品名も結合）
    private static final String SELECT_DETAILS_BY_ORDER_ID_SQL = 
        "SELECT d.quantity, d.price, p.product_name " +
        "FROM order_details d " +
        "JOIN product p ON d.product_id = p.product_id " +
        "WHERE d.order_id = ?";
    
//    MYSQL用
//    public int insertOrder(Connection conn, int totalPrice) throws SQLException {
//        // RETURN_GENERATED_KEYS を指定して、自動採番された ID を取得可能にする
//        try (PreparedStatement ps = conn.prepareStatement(INSERT_ORDER_SQL, Statement.RETURN_GENERATED_KEYS)) {
//            ps.setInt(1, totalPrice);
//            ps.executeUpdate();
//
//            // 発行された order_id を取得
//            try (ResultSet rs = ps.getGeneratedKeys()) {
//                if (rs.next()) {
//                    return rs.getInt(1);
//                } else {
//                    return 0;
//                }
//            }
//        }
//    }
    
    public int insertOrder(Connection conn, int totalPrice) throws SQLException {
        // 【重要】Oracleでは、取得したいカラム名を明示的に指定します
        String[] generatedColumns = {"order_id"}; 
        
        try (PreparedStatement ps = conn.prepareStatement(INSERT_ORDER_SQL, generatedColumns)) {
            ps.setInt(1, totalPrice);
            ps.executeUpdate();

            // 発行された ID を取得
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    // Oracleではカラム名を指定して取得するのが最も安全です
                    return rs.getInt(1); 
                } else {
                    return 0;
                }
            }
        }
    }
    
    public List<OrderHistoryDTO> findAllOrders() {
        List<OrderHistoryDTO> orderList = new ArrayList<>();
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_ORDERS_SQL);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                OrderHistoryDTO dto = new OrderHistoryDTO();
                dto.setOrderId(rs.getInt("order_id"));
                dto.setOrderDate(rs.getTimestamp("order_date"));
                dto.setTotalPrice(rs.getInt("total_price"));
                
                // 各注文に対して明細を取得してセットする
                dto.setDetails(findDetailsByOrderId(dto.getOrderId()));
                orderList.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    private List<OrderDetailDTO> findDetailsByOrderId(int orderId) {
        List<OrderDetailDTO> details = new ArrayList<>();
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_DETAILS_BY_ORDER_ID_SQL)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderDetailDTO d = new OrderDetailDTO();
                    d.setProductName(rs.getString("product_name"));
                    d.setQuantity(rs.getInt("quantity"));
                    d.setPrice(rs.getInt("price"));
                    details.add(d);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return details;
    }
}