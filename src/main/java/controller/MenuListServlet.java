package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dto.CategoryDTO;
import model.dto.OptionDTO;
import model.dto.ProductDTO;
import model.service.CustomerService;
import viewmodel.MenuListViewModel;

/**
 * Servlet implementation class MenuListServlet
 */
@WebServlet("/MenuListServlet")
public class MenuListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	// 業務ロジッククラスのインスタンス
    private CustomerService customerService = new CustomerService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
            // 1. Serviceを使ってデータを取得
            List<CategoryDTO> categories = customerService.getCategoryList();
            List<ProductDTO> products = customerService.getProductList();
            List<OptionDTO> options = customerService.getOptionList();
            
            // 2. ViewModelにデータを格納
            MenuListViewModel viewModel = new MenuListViewModel();
            viewModel.setCategoryList(categories);
            viewModel.setProductList(products);
            viewModel.setOptionList(options);
            
            // 3. リクエストスコープに保存
            request.setAttribute("viewModel", viewModel);
            
            // 4. JSPへフォワード
            // ※ "/WEB-INF/view/menu_list.jsp" のパスは実際の配置に合わせてください
            request.getRequestDispatcher("/WEB-INF/views/menu_list.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            // エラー時はエラー画面などへ
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
