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
import model.service.ProductService;

/**
 * Servlet implementation class ProductDetailServlet
 */
@WebServlet("/ProductDetailServlet")
public class ProductDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private CustomerService customerService = new CustomerService();
	private ProductService productService = new ProductService();
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 商品IDを取得
				String idStr = request.getParameter("id");

				// ★追加: IDがない場合（nullチェック）
				if (idStr == null || idStr.isEmpty()) {
					// IDがないので、メニュー一覧に戻す
					response.sendRedirect("MenuListServlet"); 
					System.out.println(idStr);
					return;
				}

				try {
					int productId = Integer.parseInt(idStr);

					// 2. 商品情報を取得
					ProductDTO product = productService.getProductById(productId);
					
					// 商品が見つからない場合も戻す
					if (product == null) {
						response.sendRedirect("MenuListServlet");
						return;
					}

					// 3. その商品に紐づくオプションリストだけを取得
					List<OptionDTO> productOptionList = productService.getOptionsByProductId(productId);

					// 4. リクエストスコープにセット
					request.setAttribute("product", product);
					request.setAttribute("optionList", productOptionList);

					// 5. JSPへフォワード
					request.getRequestDispatcher("/WEB-INF/views/product_detail.jsp").forward(request, response);

				} catch (NumberFormatException e) {
					// IDが数字じゃない場合も一覧に戻す
					System.out.println(idStr);
					e.printStackTrace();
					response.sendRedirect("MenuListServlet");
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
