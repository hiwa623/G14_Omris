package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import dao.KitchenDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dto.KitchenRowDTO;
import viewmodel.KitchenRowViewModel;
import viewmodel.KitchenViewModelFactory;

@WebServlet("/kitchen")
public class KitchenServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        KitchenDAO dao = new KitchenDAO();

        try {
            List<KitchenRowDTO> dtoList = dao.findActiveRows();
            List<KitchenRowViewModel> vmList =
                    KitchenViewModelFactory.toConfirmVMs(dtoList, request.getContextPath());

            request.setAttribute("kitchenList", vmList);
            request.getRequestDispatcher("/WEB-INF/jsp/kitchen.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "注文料理一覧の取得に失敗した");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }
}
