package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CashRegisterInputServlet
 */
@WebServlet("/CashRegisterInputServlet")
public class CashRegisterInputServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 入力画面を表示
        request.getRequestDispatcher("/WEB-INF/views/CashRegisterInput.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
request.setCharacterEncoding("UTF-8");
        
        // 入力されたテーブル番号を取得
        String tableNo = request.getParameter("table_no");
        
        // 次の画面（確認・支払い画面）へリダイレクト
        // URLパラメータとして table_no を渡す
        response.sendRedirect(request.getContextPath() + "/CashRegisterPaymentServlet?table_no=" + tableNo);
	}

}
