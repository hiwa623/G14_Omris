package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dto.SalesDTO;

public class SalesDAO {

	// SQL: 日付ごとにグループ化して合計を計算 (Oracle用)
	private static final String sql = "SELECT TO_CHAR(o.order_date, 'YYYY-MM-DD') AS day, "
			+ "       SUM(od.price * od.quantity) AS total "
			+ "FROM orders o "
			+ "JOIN order_details od ON o.order_id = od.order_id "
			+ "WHERE od.product_status_id = 'PAID' "
			+ "  AND TRUNC(o.order_date) BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD') " // ★期間指定
			+ "GROUP BY TO_CHAR(o.order_date, 'YYYY-MM-DD') "
			+ "ORDER BY day DESC";

	// SQL: 商品ごとに数量と金額を集計して売上金額順に並べる
	private static final String sql2 = "SELECT p.product_name, "
            + "       SUM(od.quantity) AS qty, "
            + "       SUM(od.price * od.quantity) AS amount "
            + "FROM order_details od "
            + "JOIN product p ON od.product_id = p.product_id "
            + "JOIN orders o ON od.order_id = o.order_id "
            + "WHERE od.product_status_id = 'PAID' "
            + "  AND TRUNC(o.order_date) BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD') "
            + "GROUP BY p.product_name "
            + "ORDER BY amount DESC";

	/**
	 * 日別の売上合計を取得する
	 */
	public List<SalesDTO> getDailySales(String startDate, String endDate) {
		List<SalesDTO> list = new ArrayList<>();

		try (Connection con = DBManager.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {
	            
	            ps.setString(1, startDate); // いつから
	            ps.setString(2, endDate);   // いつまで

	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {
	                    SalesDTO dto = new SalesDTO();
	                    dto.setSalesDate(rs.getString("day"));
	                    dto.setTotalSales(rs.getInt("total"));
	                    list.add(dto);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return list;
	}

	/**
	 * 商品別の売上ランキングを取得する
	 */
	public List<SalesDTO> getProductRanking(String startDate, String endDate) {
		List<SalesDTO> list = new ArrayList<>();

		try (Connection con = DBManager.getConnection();
				PreparedStatement ps = con.prepareStatement(sql2)) {

			ps.setString(1, startDate);
			ps.setString(2, endDate);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					SalesDTO dto = new SalesDTO();
					dto.setProductName(rs.getString("product_name"));
					dto.setTotalQuantity(rs.getInt("qty"));
					dto.setTotalSales(rs.getInt("amount"));
					list.add(dto);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
