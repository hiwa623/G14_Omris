package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dto.HallRowDTO;

public class HallDAO {

    public static final String STATUS_COOKED = "COOKED";
    public static final String STATUS_SERVED = "SERVED";

    /**
     * 提供前一覧（COOKED）
     */
    public List<HallRowDTO> findCookedRows(Connection con) throws SQLException {

        String sql =
            "SELECT od.detail_id, p.product_name, od.quantity, tm.table_no, " +
            "       od.product_status_id, ps.name AS status_name, " +
            "       o.order_date AS sort_time, od.updated_at " +
            "  FROM order_details od " +
            "  JOIN product p ON od.product_id = p.product_id " +
            "  JOIN orders o ON od.order_id = o.order_id " +
            "  JOIN table_master tm ON o.table_id = tm.id " +
            "  JOIN product_status ps ON od.product_status_id = ps.id " +
            " WHERE od.product_status_id = ? " +
            " ORDER BY o.order_date ASC, od.detail_id ASC";

        List<HallRowDTO> list = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, STATUS_COOKED);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }

    /**
     * 提供済み一覧（SERVED かつ 30分以内）
     */
    public List<HallRowDTO> findServedRowsWithin30Min(Connection con) throws SQLException {

        String sql =
            "SELECT od.detail_id, p.product_name, od.quantity, tm.table_no, " +
            "       od.product_status_id, ps.name AS status_name, " +
            "       od.updated_at AS sort_time, od.updated_at " +
            "  FROM order_details od " +
            "  JOIN product p ON od.product_id = p.product_id " +
            "  JOIN orders o ON od.order_id = o.order_id " +
            "  JOIN table_master tm ON o.table_id = tm.id " +
            "  JOIN product_status ps ON od.product_status_id = ps.id " +
            " WHERE od.product_status_id = ? " +
            "   AND od.updated_at >= (SYSTIMESTAMP - INTERVAL '30' MINUTE) " +
            " ORDER BY od.updated_at DESC, od.detail_id DESC";

        List<HallRowDTO> list = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, STATUS_SERVED);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }

    /**
     * 配膳完了（COOKED → SERVED）
     */
    public int markServed(Connection con, long orderDetailId) throws SQLException {
        String sql =
            "UPDATE order_details " +
            "   SET product_status_id = ?, updated_at = SYSTIMESTAMP " +
            " WHERE detail_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, STATUS_SERVED);
            ps.setLong(2, orderDetailId);
            return ps.executeUpdate();
        }
    }

    /**
     * 提供済みキャンセル（SERVED → COOKED）
     */
    public int cancelServed(Connection con, long orderDetailId) throws SQLException {
        String sql =
            "UPDATE order_details " +
            "   SET product_status_id = ?, updated_at = SYSTIMESTAMP " +
            " WHERE detail_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, STATUS_COOKED);
            ps.setLong(2, orderDetailId);
            return ps.executeUpdate();
        }
    }

    private HallRowDTO mapRow(ResultSet rs) throws SQLException {
        return new HallRowDTO(
            rs.getLong("detail_id"),
            rs.getString("product_name"),
            rs.getLong("quantity"),
            rs.getString("table_no"),
            rs.getString("product_status_id"),
            rs.getString("status_name"),
            rs.getTimestamp("sort_time"),
            rs.getTimestamp("updated_at")
        );
    }
}
