package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.service.ProductService;

/**
 * Servlet implementation class DeleteProductServlet
 * 店長管理画面の商品一覧画面の削除ボタンが押されたときのサーブレット
 */
@WebServlet("/DeleteProductServlet")
public class DeleteProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductService productService = new ProductService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// IDパラメータの取得
		String idStr = request.getParameter("id");

		// IDがない場合は一覧へ戻す
		if (idStr == null || idStr.isEmpty()) {
			response.sendRedirect("ManagerServlet");
			return; // ★ここで終了
		}
		
		try {
			int productId = Integer.parseInt(idStr);

			// 削除実行
			boolean isSuccess = productService.deleteProduct(productId);

			if (isSuccess) {
				// 成功時
				response.sendRedirect("AdminLineupServlet?msg=deleted");
			} else {
				// 失敗時（注文履歴がある場合など）
				// エラーメッセージ用のパラメータをつけて戻る
				response.sendRedirect("ManagerServlet?error=delete_failed");
			}
			return;

		} catch (NumberFormatException e) {
			e.printStackTrace();
			// 数値変換エラーの場合
			response.sendRedirect("ManagerServlet");
			return;
		} catch (Exception e) {
            e.printStackTrace();
            // その他の予期せぬエラー
            response.sendRedirect("ManagerServlet?error=unknown");
            return;
        }
		// 処理が終わったら一覧画面（管理メニュー）へリダイレクト
		// ★ "ManagerServlet" はご自身の環境の商品一覧サーブレット名に合わせてください
//		response.sendRedirect("ManagerServlet");
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
