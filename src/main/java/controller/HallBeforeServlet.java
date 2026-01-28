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

@WebServlet("/HallBeforeServlet")
public class HallBeforeServlet extends HttpServlet {

    private final HallService service = new HallService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<HallRowDTO> dtoList = service.getCookedRows();
            List<HallRowViewModel> vmList =
                    HallViewModelFactory.toServeConfirmVMs(dtoList, request.getContextPath());

            request.setAttribute("beforeList", vmList);
            request.getRequestDispatcher("/WEB-INF/views/hall.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "提供前一覧の取得に失敗しました");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
