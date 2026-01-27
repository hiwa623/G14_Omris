package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dto.CategoryDTO;
import model.service.CategoryService;

/**
 * Servlet implementation class CategoryControlServlet
 */
@WebServlet("/CategoryControlServlet")
public class CategoryControlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CategoryService categoryService = new CategoryService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<CategoryDTO> list = categoryService.getAllCategories();
        request.setAttribute("categoryList", list);
        request.getRequestDispatcher("/WEB-INF/views/admin-category.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");

        boolean result = categoryService.addCategory(name);
        
        // 処理完了後、一覧画面にリダイレクト（再読み込み対策）
        response.sendRedirect("CategoryControlServlet");
	}

}
