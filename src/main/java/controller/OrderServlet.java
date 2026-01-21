package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dto.CartItemDTO;
import model.service.OrderService;
import viewmodel.OrderViewModel;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderService orderService = new OrderService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        List<CartItemDTO> cart = (List<CartItemDTO>) session.getAttribute("cart");

        int tableId = 1; 
        String tableParam = request.getParameter("tableId");
        if (tableParam != null) {
            tableId = Integer.parseInt(tableParam);
        }
        
        OrderViewModel vm = new OrderViewModel();

        if (cart == null || cart.isEmpty()) {
            vm.setSuccess(false);
            vm.setMessage("カートが空です。");
        } else if (orderService.checkout(cart, tableId)) {
            vm.setSuccess(true);
            vm.setMessage("ご注文ありがとうございました！");
            session.removeAttribute("cart");
        } else {
            vm.setSuccess(false);
            vm.setMessage("システムエラーにより注文を確定できませんでした。");
        }

        request.setAttribute("vm", vm);
        // 完了画面、またはエラー表示のためにカート画面へ
        String nextView = vm.isSuccess() ? "/WEB-INF/views/thanks.jsp" : "/WEB-INF/views/cart.jsp";
        request.getRequestDispatcher(nextView).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
