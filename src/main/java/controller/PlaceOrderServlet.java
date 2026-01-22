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
import model.service.CustomerService;

/**
 * Servlet implementation class PlaceOrderServlet
 */
@WebServlet("/PlaceOrderServlet")
public class PlaceOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CustomerService customerService = new CustomerService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		try {
			// 1. セッションから必要な情報を取得
			@SuppressWarnings("unchecked")
			List<CartItemDTO> cart = (List<CartItemDTO>) session.getAttribute("cart");
			Integer tableId = (Integer) session.getAttribute("tableId");
			Integer customerCount = (Integer) session.getAttribute("customerCount");

			// 念のためnullチェック（カートが空、またはセッション切れの場合）
			if (cart == null || cart.isEmpty() || tableId == null) {
				response.sendRedirect("MenuListServlet"); // メニューへ戻す
				return;
			}

			// 人数がnullの場合はとりあえず1名として扱う（安全策）
			if (customerCount == null) {
				customerCount = 1;
			}

			// 2. サービスを呼び出して注文確定（DB登録）
			// ※CustomerService.placeOrderの戻り値は注文ID(int)と想定
			int orderId = customerService.placeOrder(cart, tableId, customerCount);

			// 3. 注文が完了したら、セッションのカートを空にする！
			session.removeAttribute("cart");

			// 注文IDをリクエストスコープに入れて完了画面へ
			request.setAttribute("orderId", orderId);
			request.getRequestDispatcher("/WEB-INF/views/thank_you.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			// エラー時はエラー画面へ（今回は簡易的にメニューへ戻します）
			response.sendRedirect("MenuListServlet");
		}
	}

}
