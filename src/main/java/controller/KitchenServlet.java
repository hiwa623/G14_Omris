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

@WebServlet("/KitchenServlet")
public class KitchenServlet extends HttpServlet {

    private final KitchenService service = new KitchenService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<KitchenRowDTO> dtoList = service.getCookingRows();
            List<KitchenRowViewModel> vmList =
                    KitchenViewModelFactory.toConfirmVMs(dtoList, request.getContextPath());

            request.setAttribute("kitchenList", vmList);
            request.getRequestDispatcher("/WEB-INF/views/kitchen.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "注文料理一覧の取得に失敗しました");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
