package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dto.HallRowDTO;
import model.service.HallService;
import viewmodel.HallRowViewModel;
import viewmodel.HallViewModelFactory;

@WebServlet("/HallServedServlet")
public class HallServedServlet extends HttpServlet {

    private final HallService service = new HallService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<HallRowDTO> dtoList = service.getServedRowsWithin30Min();
            List<HallRowViewModel> vmList =
                    HallViewModelFactory.toCancelConfirmVMs(dtoList, request.getContextPath());

            request.setAttribute("servedList", vmList);
            request.getRequestDispatcher("/WEB-INF/views/hallServed.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "提供済み一覧の取得に失敗しました");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
