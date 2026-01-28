package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.service.KitchenService;

@WebServlet("/kitchen/cancelComplete")
public class KitchenCancelCompleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("orderDetailId");
        if (idParam == null || idParam.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/kitchen/completed");
            return;
        }

        try {
            long orderDetailId = Long.parseLong(idParam);
            new KitchenService().cancelCompleted(orderDetailId);

        } catch (NumberFormatException ignored) {
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 仕様：提供済み一覧に戻す（タブ操作に合わせる）
        response.sendRedirect(request.getContextPath() + "/kitchen/completed");
    }
}
