package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dto.KitchenRowDTO;
import model.service.KitchenService;
import viewmodel.KitchenRowViewModel;
import viewmodel.KitchenViewModelFactory;

@WebServlet("/KitchenCompletedServlet")
public class KitchenCompletedServlet extends HttpServlet {

    private final KitchenService service = new KitchenService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<KitchenRowDTO> dtoList = service.getCookedRows();
            List<KitchenRowViewModel> vmList =
                    KitchenViewModelFactory.toCancelConfirmVMs(dtoList, request.getContextPath());

            request.setAttribute("completedList", vmList);
            request.getRequestDispatcher("/WEB-INF/views/kitchenCompleted.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "完了済み商品の取得に失敗しました");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
