package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.service.KitchenService;
import viewmodel.KitchenCompletedViewModel;

@WebServlet("/kitchen/completed")
public class KitchenCompletedServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        KitchenService service = new KitchenService();

        try {
            KitchenCompletedViewModel vm = service.getKitchenCompletedViewModel(request.getContextPath());
            request.setAttribute("vm", vm);
            request.getRequestDispatcher("/WEB-INF/views/kitchenCompleted.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "完了済み商品の取得に失敗した");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
