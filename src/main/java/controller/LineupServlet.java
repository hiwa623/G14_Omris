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
 * Servlet implementation class LineupServlet
 */
@WebServlet("/LineupServlet")
public class LineupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	//依存するServiceインスタンスを生成
	private ProductService productService = new ProductService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			//Serviseを呼び出し、DTOリストを取得
			List<ProductDTO> productList = productService.getProductListForDisplay();

			//ViewModelを生成し、画面表示に必要なデータをセット
			LineupViewModel viewModel = new LineupViewModel();
			viewModel.setProductList(productList);
			//全体の件数表示（必要なら実行）
//			viewModel.setTotalItemCount(productList.size());

			//商品が0件だった場合に実行
			if(productList.isEmpty()) {
				viewModel.setErrorMessage("表示できる商品が見つかりませんでした");
			}
			
			//ViewModelをリクエストスコープにセット
			request.setAttribute("vm", viewModel);
			
			//Lineup.jspにフォワード
			request.getRequestDispatcher("/WEB-INF/views/Lineup.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			
			// エラー情報をリクエストスコープにセットしてエラーページにフォワードする
            request.setAttribute("error_message", "商品一覧の読み込み中にシステムエラーが発生しました。");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
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
