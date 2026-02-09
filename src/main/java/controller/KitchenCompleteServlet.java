package controller;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.service.KitchenService;

@WebServlet("/KitchenCompleteServlet")
public class KitchenCompleteServlet extends HttpServlet {

    private final KitchenService service = new KitchenService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            long orderDetailId = Long.parseLong(request.getParameter("orderDetailId"));
            service.markCooked(orderDetailId);
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/KitchenServlet");
    }
}
