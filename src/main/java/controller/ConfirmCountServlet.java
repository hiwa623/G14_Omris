package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class ConfirmCountServlet
 */
@WebServlet("/ConfirmCountServlet")
public class ConfirmCountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

        try {
            // 1. 人数を取得
            String countStr = request.getParameter("customerCount");
            int customerCount = Integer.parseInt(countStr);
            
            // 2. セッションに保存
            HttpSession session = request.getSession();
            session.setAttribute("customerCount", customerCount);
            
            // 3. メニュー一覧へリダイレクト
            // MenuListServlet のURLに合わせてください
            response.sendRedirect("MenuListServlet");

        } catch (Exception e) {
            e.printStackTrace();
            // エラー時はStartOrderServlet(最初)に戻す
            response.sendRedirect("StartOrderServlet");
        }
	}

}
