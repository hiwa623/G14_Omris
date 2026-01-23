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
 * Servlet implementation class SalesDailyServlet
 */
@WebServlet("/SalesDailyServlet")
public class SalesDailyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SalesService salesService = new SalesService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// パラメータ取得
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        // 日付がない場合（初回表示時など）は「今月の1日」～「今日」をセット
        if (startDate == null || startDate.isEmpty()) {
            LocalDate today = LocalDate.now();
            startDate = today.withDayOfMonth(1).format(DateTimeFormatter.ISO_DATE); // YYYY-MM-01
            endDate = today.format(DateTimeFormatter.ISO_DATE); // YYYY-MM-DD
        }

        // データ取得
        List<SalesDTO> list = salesService.getDailySales(startDate, endDate);

        request.setAttribute("dailySalesList", list);
        request.setAttribute("startDate", startDate); // 画面の入力欄に残すため
        request.setAttribute("endDate", endDate);

        request.getRequestDispatcher("/WEB-INF/views/admin-sales-daily.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
