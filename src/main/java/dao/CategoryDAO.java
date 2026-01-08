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
}
