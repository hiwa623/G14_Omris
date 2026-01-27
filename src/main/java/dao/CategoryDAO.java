package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dto.CategoryDTO;

public class CategoryDAO {
    // SQL: id順に並べて取得
    private static final String SELECT_ALL_SQL = "SELECT id, name, created_at, updated_at FROM category ORDER BY id";

 // ★追加: カテゴリ追加用SQL
    private static final String INSERT_SQL = "INSERT INTO category (name) VALUES (?)";
    
    /**
     * 全カテゴリーを取得する
     */
    public List<CategoryDTO> findAll() {
        List<CategoryDTO> list = new ArrayList<>();

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CategoryDTO dto = new CategoryDTO();
                // DBの「id」を DTOの「categoryId」へ
                dto.setCategoryId(rs.getInt("id"));
                // DBの「name」を DTOの「categoryName」へ
                dto.setCategoryName(rs.getString("name"));
                // 時間情報も入れる
                dto.setCreateAt(rs.getTimestamp("created_at"));
                dto.setUpdateAt(rs.getTimestamp("updated_at"));
                
                list.add(dto);
            }
        } catch (SQLException e) {
            System.err.println("カテゴリー取得エラー: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
    
    /**
     * 新しいカテゴリーを登録する
     * @param name カテゴリー名
     * @return 成功ならtrue
     */
    public boolean insert(String name) {
        // DBManagerを使用して接続を取得
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
            
            ps.setString(1, name);
            
            int result = ps.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            System.err.println("カテゴリー登録エラー: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
