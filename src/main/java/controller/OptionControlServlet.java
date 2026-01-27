package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dto.OptionDTO;
import model.service.OptionService;

/**
 * Servlet implementation class OptionControlServlet
 */
@WebServlet("/OptionControlServlet")
public class OptionControlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OptionService optionService = new OptionService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<OptionDTO> list = optionService.getAllOptions();
        request.setAttribute("optionList", list);
        request.getRequestDispatcher("/WEB-INF/views/admin-option.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        String price = request.getParameter("price");

        optionService.addOption(name, price);
        
        response.sendRedirect("OptionControlServlet");
	}

}
