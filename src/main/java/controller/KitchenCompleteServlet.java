package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.service.KitchenService;

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
            new KitchenService().markCompleted(orderDetailId);

        } catch (NumberFormatException ignored) {
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 完了後は完了済みへ
        response.sendRedirect(request.getContextPath() + "/kitchen/completed");
    }
}
