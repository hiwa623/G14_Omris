package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dto.KitchenRowDTO;

public class KitchenDAO {

    public static final String STATUS_NEW = "NEW";
    public static final String STATUS_COOKING = "COOKING";
    public static final String STATUS_SERVED = "SERVED";

    /**
     * 提供前一覧（NEW / COOKING）
     */
    public List<KitchenRowDTO> findActiveRows() throws SQLException {

        String sql =
            "SELECT od.detail_id, p.product_name, od.quantity, tm.table_no, " +
            "       od.product_status_id, ps.name AS status_name, " +
            "       o.order_date AS sort_time, od.updated_at " +
            "  FROM order_details od " +
            "  JOIN product p ON od.product_id = p.product_id " +
            "  JOIN orders o ON od.order_id = o.order_id " +
            "  JOIN table_master tm ON o.table_id = tm.id " +
            "  JOIN product_status ps ON od.product_status_id = ps.id " +
            " WHERE od.product_status_id IN (?, ?) " +
            " ORDER BY o.order_date ASC, od.detail_id ASC";

        List<KitchenRowDTO> list = new ArrayList<>();

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, STATUS_NEW);
            ps.setString(2, STATUS_COOKING);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    /**
     * 提供済み一覧（30分以内のみ）
     */
    public List<KitchenRowDTO> findCompletedRows() throws SQLException {

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
            " ORDER BY od.updated_at DESC";

        List<KitchenRowDTO> list = new ArrayList<>();

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, STATUS_SERVED);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    /** 完了（＝提供済み） */
    public void markCompleted(long detailId) throws SQLException {
        updateStatus(detailId, STATUS_SERVED);
    }

    /** 完了取消（＝調理中に戻す） */
    public void cancelCompleted(long detailId) throws SQLException {
        updateStatus(detailId, STATUS_COOKING);
    }

    /** ステータス更新共通 */
    private void updateStatus(long detailId, String status) throws SQLException {

        String sql =
            "UPDATE order_details " +
            "   SET product_status_id = ?, updated_at = SYSTIMESTAMP " +
            " WHERE detail_id = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setLong(2, detailId);
            ps.executeUpdate();
        }
    }

    /** DTO共通マッピング */
    private KitchenRowDTO mapRow(ResultSet rs) throws SQLException {

        return new KitchenRowDTO(
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
