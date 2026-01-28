package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dto.OrderHistoryDTO;
import model.service.CustomerService;
import viewmodel.OrderHistoryViewModel;

/**
 * Servlet implementation class OrderHistoryServlet
 */
@WebServlet("/OrderHistoryServlet")
public class OrderHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// サービスを呼び出す準備
	private CustomerService customerService = new CustomerService();
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		// セッションからテーブルIDを取得（ログイン的な処理）
		Integer tableId = (Integer) session.getAttribute("tableId");

		// セッション切れ対策（テーブルIDがないならトップへ戻す）
		if (tableId == null) {
			response.sendRedirect("index.jsp");
			return;
		}

		// ★ここでさっき作ったDAOのメソッドが間接的に呼ばれます
		List<OrderHistoryDTO> historyList = customerService.getOrderHistory(tableId);

//		ViewModelを使ってデータを渡す
        OrderHistoryViewModel vm = new OrderHistoryViewModel();
        vm.setHistoryList(historyList);
        
        if (historyList == null || historyList.isEmpty()) {
            vm.setMessage("まだ注文履歴がありません。");
        }
        
     // ViewModelをリクエストスコープにセット
        request.setAttribute("vm", vm);

		// 画面を表示する
		request.getRequestDispatcher("/WEB-INF/views/order_history.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
