package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import dao.HallDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dto.HallRowDTO;
import viewmodel.HallRowViewModel;
import viewmodel.HallViewModelFactory;

@WebServlet("/hall/served")
public class HallServedServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HallDAO dao = new HallDAO();

        try {
            List<HallRowDTO> dtoList = dao.findServedRowsWithin30Min();
            List<HallRowViewModel> vmList =
                    HallViewModelFactory.toCancelConfirmVMs(dtoList, request.getContextPath());

            request.setAttribute("servedList", vmList);
            request.getRequestDispatcher("/WEB-INF/jsp/hallServed.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "提供済み一覧の取得に失敗した");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }
}
