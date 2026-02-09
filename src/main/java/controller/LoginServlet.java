package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dto.LoginDTO;
import model.service.LoginService;
import viewmodel.LoginViewModel;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// 業務ロジッククラスのインスタンス
	private LoginService service = new LoginService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//. 初回表示用の空のViewModelを作成
		LoginViewModel vm = new LoginViewModel();

		// 2. JSPに渡すためにリクエストスコープにセット
		request.setAttribute("viewModel", vm);

		// 3. 画面（JSP）へフォワード
		// ※WEB-INF配下に置くことで、URL直打ちによる不正アクセスを防ぎます
		request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 文字化け対策
		request.setCharacterEncoding("UTF-8");

		// 1. 入力値の取得
		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");

		// 2. Serviceクラスを使って認証チェック
		LoginDTO user = service.authenticate(loginId, password);

		if (user != null) {
			// ■ ログイン成功時

			// セッションにユーザー情報を保存
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", user);

			// 役割（ロール）によって遷移先を振り分け
			response.sendRedirect("ManagerServlet");

		} else {
			// ■ ログイン失敗時

			// エラー情報をViewModelに詰める
			LoginViewModel vm = new LoginViewModel();
			vm.setLoginId(loginId); // 入力されたIDを戻してあげる（再入力の手間を省く）
			vm.setErrorMessage("IDまたはパスワードが正しくありません。");

			// リクエストスコープにセット
			request.setAttribute("viewModel", vm);

			// ログイン画面を再表示
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
	}

}
