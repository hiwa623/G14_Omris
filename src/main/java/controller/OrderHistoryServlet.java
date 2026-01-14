package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dto.OrderHistoryDTO;
import model.service.OrderService;
import viewmodel.OrderHistoryViewModel;

/**
 * Servlet implementation class OrderHistoryServlet
 */
@WebServlet("/OrderHistoryServlet")
public class OrderHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderService orderService = new OrderService();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. Serviceからデータを取得
        List<OrderHistoryDTO> historyList = orderService.getOrderHistory();
        
        // 2. ViewModelに詰める
        OrderHistoryViewModel vm = new OrderHistoryViewModel();
        vm.setHistoryList(historyList);
        
        if (historyList.isEmpty()) {
            vm.setMessage("注文履歴はまだありません。");
        }
        
        // 3. JSPへフォワード
        request.setAttribute("vm", vm);
        request.getRequestDispatcher("/WEB-INF/views/orderhistory.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
