package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dto.ProductDTO;
import model.service.ProductService;
import viewmodel.LineupViewModel;

/**
 * Servlet implementation class AdminLineupServlet
 */
@WebServlet("/AdminLineupServlet")
public class AdminLineupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//店長管理画面登録商品一覧
	private ProductService productService = new ProductService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 1. 全商品を取得
		List<ProductDTO> productList = productService.getProductListForDisplay();

		// 2. ViewModelにセット
		LineupViewModel vm = new LineupViewModel();
		vm.setProductList(productList);
		vm.setTotalItemCount(productList.size());

		// 完了メッセージなどの処理（削除後など）
		String status = request.getParameter("status");
		if ("deleted".equals(status)) {
			vm.setErrorMessage("商品を削除しました。"); // メッセージ枠を流用
		} else if ("updated".equals(status)) {
			vm.setErrorMessage("商品情報を更新しました。");
		}

		// 3. "vm" としてリクエストスコープに保存
		request.setAttribute("vm", vm);

		// 管理画面用JSPへ移動
		request.getRequestDispatcher("/WEB-INF/views/admin-lineup.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
