package controller;

import java.io.IOException;
import java.sql.SQLException;

import dao.KitchenDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/kitchen/complete")
public class KitchenCompleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("orderDetailId");
        if (idParam == null || idParam.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/kitchen");
            return;
        }

        try {
            long orderDetailId = Long.parseLong(idParam);
            new KitchenDAO().markCompleted(orderDetailId);
        } catch (NumberFormatException ignored) {
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/kitchen/completed");
    }
}
