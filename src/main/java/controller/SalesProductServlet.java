package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dto.SalesDTO;
import model.service.SalesService;

/**
 * Servlet implementation class SalesProductServlet
 */
@WebServlet("/SalesProductServlet")
public class SalesProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SalesService salesService = new SalesService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// パラメータ取得
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        // デフォルト日付設定
        if (startDate == null || startDate.isEmpty()) {
            LocalDate today = LocalDate.now();
            startDate = today.withDayOfMonth(1).format(DateTimeFormatter.ISO_DATE);
            endDate = today.format(DateTimeFormatter.ISO_DATE);
        }

        // データ取得（こちらはランキングメソッドを呼ぶ）
        List<SalesDTO> list = salesService.getProductRanking(startDate, endDate);

        request.setAttribute("rankingList", list);
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);

        request.getRequestDispatcher("/WEB-INF/views/admin-sales-product.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
