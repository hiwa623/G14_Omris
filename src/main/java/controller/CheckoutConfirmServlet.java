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

/**
 * Servlet implementation class CheckoutConfirmServlet
 */
@WebServlet("/CheckoutConfirmServlet")
public class CheckoutConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// 既存のサービスを利用
    private CustomerService customerService = new CustomerService();
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Integer tableId = (Integer) session.getAttribute("tableId");
		String tableNo;
		
		// セッション切れ対策
        if (tableId == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        
		if (tableId == 1) {
			tableNo = "C-1";
		} else if (tableId == 2) {
			tableNo = "C-2";
		} else if (tableId == 3) {
			tableNo = "C-3";
		} else if (tableId == 4) {
			tableNo = "C-4";
		} else if (tableId == 5) {
			tableNo = "C-5";
		} else if (tableId == 6) {
			tableNo = "B-1";
		} else if (tableId == 7) {
			tableNo = "B-2";
		} else if (tableId == 8) {
			tableNo = "B-3";
		} else if (tableId == 9) {
			tableNo = "B-4";
		} else if (tableId == 10) {
			tableNo = "B-5";
		} else if (tableId == 11) {
			tableNo = "B-6";
		} else {
			tableNo = "NOT FOUND";
		}
        

        // 1. 未会計の注文履歴を取得
        List<OrderHistoryDTO> billList = customerService.getOrderHistory(tableId);

        // 2. 合計金額を計算（複数回注文している場合、全ての合計が必要）
        int grandTotal = 0;
        if (billList != null) {
            for (OrderHistoryDTO order : billList) {
                grandTotal += order.getTotalPrice();
            }
        }

        // 3. JSPにデータを渡す
        request.setAttribute("tableNo", tableNo);
        request.setAttribute("billList", billList); // 明細も表示したい場合用
        request.setAttribute("grandTotal", grandTotal); // 合計金額

        request.getRequestDispatcher("/WEB-INF/views/checkout_confirm.jsp").forward(request, response);
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
