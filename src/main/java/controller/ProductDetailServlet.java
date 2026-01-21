package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dto.OptionDTO;
import model.dto.ProductDTO;
import model.service.CustomerService;

/**
 * Servlet implementation class ProductDetailServlet
 */
@WebServlet("/ProductDetailServlet")
public class ProductDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private CustomerService customerService = new CustomerService();
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
            // 1. URLパラメータから商品IDを取得
            String idStr = request.getParameter("productId");
            int productId = Integer.parseInt(idStr);
            
            // 2. 商品情報を取得
            ProductDTO product = customerService.getProductById(productId);
            
            // 3. オプション一覧も取得（トッピングなどを選ばせるため）
            List<OptionDTO> optionList = customerService.getOptionList();
            
            // 4. リクエストスコープにセット
            request.setAttribute("product", product);
            request.setAttribute("optionList", optionList);
            
            // 5. 詳細画面JSPへ
            request.getRequestDispatcher("/WEB-INF/views/product_detail.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("MenuListServlet"); // エラーなら一覧に戻す
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
