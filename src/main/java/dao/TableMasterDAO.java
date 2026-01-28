package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableMasterDAO {

    /**
     * 指定されたテーブル番号のステータスを更新する
     * @param tableNo テーブル番号 (例: "C-1")
     * @param status 設定するステータス (0:空席, 1:使用中など)
     * @return 更新件数
     * @throws SQLException
     */
    public int updateStatus(String tableNo, int status) throws SQLException {
        String sql = "UPDATE table_master SET status = ? WHERE table_no = ?";
        
        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, status);
            ps.setString(2, tableNo);
            
            return ps.executeUpdate();
        }
    }
    
 // 既存のクラスに追加
    public int getTableId(String tableNo) throws SQLException {
        String sql = "SELECT id FROM table_master WHERE table_no = ?";
        
        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, tableNo);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return 0; // 見つからない場合
    }
    
    /**
     * テーブルIDを指定してステータスを更新する
     * @param tableId テーブルID
     * @param status 設定するステータス (0:空席, 1:使用中)
     */
    public void updateStatusById(int tableId, int status) throws SQLException {
        String sql = "UPDATE table_master SET status = ? WHERE id = ?";
        
        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, status);
            ps.setInt(2, tableId); // IDで指定
            
            ps.executeUpdate();
        }
    }
}