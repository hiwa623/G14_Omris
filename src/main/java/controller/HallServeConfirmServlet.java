package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/hall/serveConfirm")
public class HallServeConfirmServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("orderDetailId");
        if (idParam == null || idParam.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/hall");
            return;
        }

        try {
            long orderDetailId = Long.parseLong(idParam);
            request.setAttribute("orderDetailId", orderDetailId);
            request.getRequestDispatcher("/WEB-INF/jsp/hallServeConfirm.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/hall");
        }
    }
}

