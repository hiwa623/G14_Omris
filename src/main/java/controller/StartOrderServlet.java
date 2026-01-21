package controller;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dto.CartItemDTO;

/**
 * Servlet implementation class StartOrderServlet
 */
@WebServlet("/StartOrderServlet")
public class StartOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// start_order.jsp を表示
        request.getRequestDispatcher("/WEB-INF/views/start_order.jsp").forward(request, response);
    }
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

        try {
            // 1. テーブル番号を取得
            String tableIdStr = request.getParameter("tableId");
            int tableId = Integer.parseInt(tableIdStr);
            
            // 2. セッションに保存
            HttpSession session = request.getSession();
            session.setAttribute("tableId", tableId);
            
            // カートの初期化もここでやっておきます
            if (session.getAttribute("cart") == null) {
                session.setAttribute("cart", new ArrayList<CartItemDTO>());
            }

            // 3. 次の画面（人数入力）へフォワード
            // ※URLは変わりませんが、表示されるJSPが切り替わります
            request.getRequestDispatcher("/WEB-INF/views/customer_count.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            // エラー時は元の画面に戻す
            response.sendRedirect("StartOrderServlet");
        }
	}

}
