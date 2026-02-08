package model.service;

import dao.LoginDAO;
import model.dto.LoginDTO;

public class LoginService {
	private LoginDAO dao = new LoginDAO();

    /**
     * ログイン認証を行う
     * @return 認証成功ならLoginDTO、失敗ならnull
     */
    public LoginDTO authenticate(String loginId, String inputPassword) {
        // 1. IDでユーザーを取得
        LoginDTO user = dao.findByLoginId(loginId);

        // 2. ユーザーが存在し、かつパスワードが一致するか確認
        if (user != null && user.getPassword().equals(inputPassword)) {
            return user; // 認証成功
        }
        
        return null; // 認証失敗
    }
}
