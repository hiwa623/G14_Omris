package controller;

import java.io.IOException;
import java.util.ArrayList;

import dao.TableMasterDAO;
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
	
	// ★追加: DAOを用意
    private TableMasterDAO tableDAO = new TableMasterDAO();

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
            // nullチェック
            if(tableIdStr == null || tableIdStr.isEmpty()){
                 response.sendRedirect("StartOrderServlet"); 
                 return;
            }
            int tableId = Integer.parseInt(tableIdStr);
            
            // 2. ★追加: ここでテーブルステータスを「1 (使用中)」に更新！
            // これでボタンを押した瞬間にステータスが変わります
            tableDAO.updateStatusById(tableId, 1);
            
            // 3. セッションに保存
            HttpSession session = request.getSession();
            session.setAttribute("tableId", tableId);
            
            // カートの初期化
            if (session.getAttribute("cart") == null) {
                session.setAttribute("cart", new ArrayList<CartItemDTO>());
            }

            // 4. 次の画面へ
            request.getRequestDispatcher("/WEB-INF/views/customer_count.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("StartOrderServlet");
        }
	}
}
