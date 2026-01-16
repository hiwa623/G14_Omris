package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dto.CartItemDTO;
import model.dto.ProductDTO;
import model.service.ProductService;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductService productService = new ProductService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//送られてきた商品IDを取得
		String productIdStr = request.getParameter("productId");
		//数量を取得するための文字列を取得
		String quantityStr = request.getParameter("quantity");

		// セッションを取得（存在しなければ新規作成）
		HttpSession session = request.getSession();
		// セッションから「cart」という名前で保存されているリストを取り出す
		List<CartItemDTO> cart = (List<CartItemDTO>) session.getAttribute("cart");
		// カートに何も追加されていなかったら
		if (cart == null) {
			// 空のリストを作成
			cart = new ArrayList<>();
		}

		// 商品追加処理が行われたかどうかの判定用
		boolean isAddedAction = false;
		ProductDTO lastProduct = null;
		int lastQuantity = 0;

		// ★重要：productId と quantity 両方が存在する場合のみ追加ロジックを動かす
		if (productIdStr != null && !productIdStr.isEmpty() && quantityStr != null && !quantityStr.isEmpty()) {
			// ブラウザから送られてきた「どの商品か」「いくつ注文したか」という情報を受け取る
			// 商品IDを取得
			int productId = Integer.parseInt(productIdStr);
			// 数量を取得（ここで null チェックをしているのでエラーにならなくなる）
			int quantity = Integer.parseInt(quantityStr);

			// 上の取得した商品IDに対応する商品情報の詳細を取得
			ProductDTO product = productService.getProductById(productId);
			lastProduct = product;
			lastQuantity = quantity;

			// 既に同じ商品がカートにあるかチェック
			// フラグを作成（同じ商品があるかどうかのフラグ）false:ない true:ある
			boolean exists = false;
			// 拡張for文
			// cart（リスト）の中の商品を上から順に取り出して、itemという名前でチェックしていく
			for (CartItemDTO item : cart) {
				// もし一致した商品があったら
				if (item.getProduct().getProductId() == productId) {
					// 商品の個数を足す
					item.setQuantity(item.getQuantity() + quantity);
					exists = true;
					// forage文の中にいる必要がなくなったので抜け出す
					break;
				}
			}
			// もし一つも一致しなかったら
			if (!exists) {
				// cart（リスト）に追加する
				cart.add(new CartItemDTO(product, quantity));
			}

			// cartをセッションに追加
			session.setAttribute("cart", cart);
			isAddedAction = true; // 追加処理が行われた
		}

		if (isAddedAction) {
			//商品追加後の中間画面へ
			viewmodel.CartAddedViewModel addedVm = new viewmodel.CartAddedViewModel();
			addedVm.setLastAddedProduct(lastProduct);
			addedVm.setAddedQuantity(lastQuantity);
			request.setAttribute("vm", addedVm);
			request.getRequestDispatcher("/WEB-INF/views/cart_added.jsp").forward(request, response);
		} else {
			// 通常のカート画面表示へ
			viewmodel.CartViewModel vm = new viewmodel.CartViewModel();
			vm.setCartItems(cart);
			request.setAttribute("vm", vm);
			request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 削除などのリクエストパラメータを判定
		//cart.jspからの<input type="hidden～>からの値を取得
		String action = request.getParameter("action");
		String productIdStr = request.getParameter("productId");

		if ("delete".equals(action) && productIdStr != null) {
			// --- 削除ロジックの追加 ---
			HttpSession session = request.getSession();
			List<CartItemDTO> cart = (List<CartItemDTO>) session.getAttribute("cart");

			if (cart != null) {
				int productId = Integer.parseInt(productIdStr);
				// IDが一致する商品をリストから削除
				cart.removeIf(item -> item.getProduct().getProductId() == productId);
				// 更新されたリストをセッションに戻す
				session.setAttribute("cart", cart);
			}
		}

		// 削除が終わった後、または通常の表示リクエストはdoGetへ
		doGet(request, response);
	}

}
