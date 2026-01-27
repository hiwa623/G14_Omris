package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/kitchen/cancelConfirm")
public class KitchenCancelConfirmServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("orderDetailId");
        if (idParam == null || idParam.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/kitchen/completed");
            return;
        }

        try {
            long orderDetailId = Long.parseLong(idParam);
            request.setAttribute("orderDetailId", orderDetailId);
            request.getRequestDispatcher("/WEB-INF/jsp/kitchenCancelConfirm.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/kitchen/completed");
        }
    }
}
