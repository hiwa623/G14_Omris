package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.service.KitchenService;
import viewmodel.KitchenToggleViewModel;

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

            KitchenService service = new KitchenService();
            KitchenToggleViewModel vm = service.getCancelConfirmViewModel(orderDetailId, request.getContextPath());

            request.setAttribute("vm", vm);
            request.getRequestDispatcher("/WEB-INF/views/kitchenCancelConfirm.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/kitchen/completed");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "確認画面の表示に失敗した");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
