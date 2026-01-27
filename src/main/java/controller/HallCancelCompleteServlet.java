package controller;

import java.io.IOException;
import java.sql.SQLException;

import dao.HallDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/hall/cancel")
public class HallCancelCompleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("orderDetailId");
        if (idParam == null || idParam.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/hall/served");
            return;
        }

        try {
            long orderDetailId = Long.parseLong(idParam);
            new HallDAO().cancelServed(orderDetailId); // SERVED → COOKING
        } catch (NumberFormatException ignored) {
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/hall/served");
    }
}
