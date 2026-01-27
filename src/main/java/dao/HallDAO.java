package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dto.HallRowDTO;

public class HallDAO {

    public static final String STATUS_NEW = "NEW";
    public static final String STATUS_COOKING = "COOKING";
    public static final String STATUS_SERVED = "SERVED";

    /**
     * 提供前一覧
     * ホールが見るのは「配膳待ち」想定なので COOKING のみ表示（※必要なら NEW も追加OK）
     */
    public List<HallRowDTO> findBeforeServeRows() throws SQLException {

        String sql =
            "SELECT od.detail_id, p.product_name, od.quantity, tm.table_no, " +
            "       od.product_status_id AS status_id, ps.name AS status_name, " +
            "       o.order_date AS sort_time, od.updated_at " +
            "  FROM order_details od " +
            "  JOIN product p ON od.product_id = p.product_id " +
            "  JOIN orders o ON od.order_id = o.order_id " +
            "  JOIN table_master tm ON o.table_id = tm.id " +
            "  JOIN product_status ps ON od.product_status_id = ps.id " +
            " WHERE od.product_status_id = ? " +
            " ORDER BY o.order_date ASC, od.detail_id ASC";

        List<HallRowDTO> list = new ArrayList<>();

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, STATUS_COOKING);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }

        return list;
    }

    /**
     * 提供済み一覧：updated_at が30分以内だけ表示（DB削除なし🔥）
     */
    public List<HallRowDTO> findServedRowsWithin30Min() throws SQLException {

        String sql =
            "SELECT od.detail_id, p.product_name, od.quantity, tm.table_no, " +
            "       od.product_status_id AS status_id, ps.name AS status_name, " +
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

    /**
     * 配膳完了：SERVEDにする（updated_at更新）
     */
    public boolean markServed(long detailId) throws SQLException {
        return updateStatus(detailId, STATUS_SERVED);
    }

    /**
     * 完了キャンセル：SERVED → COOKING に戻す（updated_at更新）
     */
    public boolean cancelServed(long detailId) throws SQLException {
        return updateStatus(detailId, STATUS_COOKING);
    }

    /**
     * ステータス更新共通（updated_atも更新）
     */
    private boolean updateStatus(long detailId, String newStatusId) throws SQLException {

        String sql =
            "UPDATE order_details " +
            "   SET product_status_id = ?, updated_at = SYSTIMESTAMP " +
            " WHERE detail_id = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newStatusId);
            ps.setLong(2, detailId);

            return ps.executeUpdate() == 1;
        }
    }

    private HallRowDTO mapRow(ResultSet rs) throws SQLException {
        return new HallRowDTO(
            rs.getLong("detail_id"),
            rs.getString("product_name"),
            rs.getLong("quantity"),
            rs.getString("table_no"),
            rs.getString("status_id"),
            rs.getString("status_name"),
            rs.getTimestamp("sort_time"),
            rs.getTimestamp("updated_at")
        );
    }
}
