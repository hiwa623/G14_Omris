package controller;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.service.HallService;

@WebServlet("/HallServeCompleteServlet")
public class HallServeCompleteServlet extends HttpServlet {

    private final HallService service = new HallService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            long orderDetailId = Long.parseLong(request.getParameter("orderDetailId"));
            service.markServed(orderDetailId);
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
        }

        // はいでも提供前一覧へ戻る仕様
        response.sendRedirect(request.getContextPath() + "/HallBeforeServlet");
    }
}
