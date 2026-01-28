package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.dto.KitchenRowDTO;

public class KitchenDAO {

    // product_status.id と一致させる
    public static final String STATUS_NEW = "NEW";
    public static final String STATUS_COOKING = "COOKING";
    public static final String STATUS_SERVED = "SERVED";

    /**
     * 注文料理一覧（提供前）: NEW / COOKING
     */
    public List<KitchenRowDTO> findActiveRows() throws SQLException {

        String sql =
            "SELECT od.detail_id, p.product_name, od.quantity, tm.table_no, " +
            "       od.product_status_id AS status_id, ps.name AS status_name, " +
            "       o.order_date AS sort_time, od.updated_at " +
            "  FROM order_details od " +
            "  JOIN product p ON od.product_id = p.product_id " +
            "  JOIN orders o ON od.order_id = o.order_id " +
            "  JOIN table_master tm ON o.table_id = tm.id " +
            "  JOIN product_status ps ON od.product_status_id = ps.id " +
            " WHERE od.product_status_id IN ('NEW', 'COOKING') " +
            " ORDER BY o.order_date ASC, od.detail_id ASC";

        List<KitchenRowDTO> list = new ArrayList<>();

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                long detailId = rs.getLong("detail_id");
                String productName = rs.getString("product_name");
                long quantity = rs.getLong("quantity");
                String tableNo = rs.getString("table_no");

                String statusId = rs.getString("status_id");
                String statusName = rs.getString("status_name");

                Timestamp sortTime = rs.getTimestamp("sort_time");
                Timestamp updatedAt = rs.getTimestamp("updated_at");

                KitchenRowDTO dto = new KitchenRowDTO(
                    detailId,
                    productName,
                    quantity,
                    tableNo,
                    statusId,
                    statusName,
                    sortTime,
                    updatedAt
                );
                list.add(dto);
            }
        }

        return list;
    }

    /**
     * 完了済み商品（提供済み）: SERVED
     * ※キッチンは30分で消さない思想（ホールのみ30分フィルタ）
     */
    public List<KitchenRowDTO> findCompletedRows() throws SQLException {

        String sql =
            "SELECT od.detail_id, p.product_name, od.quantity, tm.table_no, " +
            "       od.product_status_id AS status_id, ps.name AS status_name, " +
            "       od.updated_at AS sort_time, od.updated_at " +
            "  FROM order_details od " +
            "  JOIN product p ON od.product_id = p.product_id " +
            "  JOIN orders o ON od.order_id = o.order_id " +
            "  JOIN table_master tm ON o.table_id = tm.id " +
            "  JOIN product_status ps ON od.product_status_id = ps.id " +
            " WHERE od.product_status_id = 'SERVED' " +
            " ORDER BY od.updated_at DESC, od.detail_id DESC";

        List<KitchenRowDTO> list = new ArrayList<>();

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                long detailId = rs.getLong("detail_id");
                String productName = rs.getString("product_name");
                long quantity = rs.getLong("quantity");
                String tableNo = rs.getString("table_no");

                String statusId = rs.getString("status_id");
                String statusName = rs.getString("status_name");

                Timestamp sortTime = rs.getTimestamp("sort_time");
                Timestamp updatedAt = rs.getTimestamp("updated_at");

                KitchenRowDTO dto = new KitchenRowDTO(
                    detailId,
                    productName,
                    quantity,
                    tableNo,
                    statusId,
                    statusName,
                    sortTime,
                    updatedAt
                );
                list.add(dto);
            }
        }

        return list;
    }

    /**
     * 調理完了（=提供済みにする）
     */
    public boolean markCompleted(long detailId) throws SQLException {
        return updateStatus(detailId, STATUS_SERVED);
    }

    /**
     * 完了キャンセル（=調理中に戻す）
     */
    public boolean cancelCompleted(long detailId) throws SQLException {
        return updateStatus(detailId, STATUS_COOKING);
    }

    /**
     * ステータス更新（updated_at も更新）
     */
    public boolean updateStatus(long detailId, String newStatusId) throws SQLException {

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
}
