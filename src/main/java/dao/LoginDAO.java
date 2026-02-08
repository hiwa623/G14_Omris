package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.dto.LoginDTO;

public class LoginDAO {
	/**
     * ログインIDを元にユーザー情報を取得する
     */
    public LoginDTO findByLoginId(String loginId) {
        LoginDTO dto = null;
        String sql = "SELECT * FROM login WHERE login_id = ?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, loginId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dto = new LoginDTO();
                    dto.setId(rs.getInt("id"));
                    dto.setLoginId(rs.getString("login_id"));
                    dto.setPassword(rs.getString("password"));
                    dto.setRole(rs.getString("role"));
                    dto.setCreatedAt(rs.getTimestamp("created_at"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // 本来はログ出力や独自の例外を投げる
        }
        return dto;
    }
}
