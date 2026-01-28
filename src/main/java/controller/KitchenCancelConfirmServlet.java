package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/KitchenCancelConfirmServlet")
public class KitchenCancelConfirmServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("orderDetailId");
        if (idParam == null || idParam.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/KitchenCompletedServlet");
            return;
        }

        request.setAttribute("orderDetailId", idParam);
        request.getRequestDispatcher("/WEB-INF/views/kitchenCancelConfirm.jsp").forward(request, response);
    }
}
