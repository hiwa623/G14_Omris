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

    // ★追加: オプション登録用のSQL
    private static final String INSERT_OPTION_SQL = 
        "INSERT INTO options (option_name, option_price) VALUES (?, ?)";

    /**
     * 全オプション取得
     */
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
                // option_limitがNULLの場合は0などが入ります（DTOの実装次第ですが基本OK）
                dto.setOptionLimit(rs.getInt("option_limit"));
                dto.setCreatedAt(rs.getTimestamp("created_at"));
                dto.setUpdatedAt(rs.getTimestamp("updated_at"));
                
                list.add(dto);
            }
        } catch (SQLException e) {
            System.err.println("オプション取得エラー: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    /**
     * ★追加: オプション登録処理
     * @param option 登録したいオプション情報が入ったDTO
     * @return 成功ならtrue
     */
    public boolean insert(OptionDTO option) {
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_OPTION_SQL)) {
            
            ps.setString(1, option.getOptionName());
            ps.setInt(2, option.getOptionPrice());
            // option_limit は今回の画面入力にないので省略（NULLまたはデフォルト値になります）
            
            int result = ps.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            System.err.println("オプション登録エラー: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}