package controller;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.service.HallService;

@WebServlet("/HallCancelCompleteServlet")
public class HallCancelCompleteServlet extends HttpServlet {

    private final HallService service = new HallService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("orderDetailId");
        if (idParam == null || idParam.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/HallServedServlet");
            return;
        }

        try {
            long orderDetailId = Long.parseLong(idParam);
            service.cancelServed(orderDetailId);
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
        }

        // 保存完了したら提供済み一覧へ戻る仕様
        response.sendRedirect(request.getContextPath() + "/HallServedServlet");
    }
}
