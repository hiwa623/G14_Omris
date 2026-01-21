package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dto.OptionDTO;

public class OptionDAO {
	// 全てのオプションを取得（ID順）
    private static final String SELECT_ALL_OPTIONS = 
        "SELECT id, option_name, option_price, option_limit, created_at, updated_at FROM options ORDER BY id";
    
    public List<OptionDTO> findAll() {
        List<OptionDTO> list = new ArrayList<>();
        
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_OPTIONS);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                OptionDTO dto = new OptionDTO();
                dto.setId(rs.getInt("id"));
                dto.setOptionName(rs.getString("option_name"));
                dto.setOptionPrice(rs.getInt("option_price"));
                dto.setOptionLimit(rs.getInt("option_limit"));
                dto.setCreatedAt(rs.getTimestamp("created_at"));
                dto.setUpdatedAt(rs.getTimestamp("updated_at"));
                
                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
