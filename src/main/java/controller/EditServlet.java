package controller;

import java.io.IOException;
import java.nio.file.Paths;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.dto.ProductDTO;
import model.service.ProductService;

/**
 * Servlet implementation class EditServlet
 */
@WebServlet("/EditServlet")
public class EditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductService productService = new ProductService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println(">>> EditServlet: doGet が呼ばれました。ID=" + request.getParameter("productId"));
        // 1. リクエストパラメータから編集したい商品のIDを取得
        String idStr = request.getParameter("productId");
        System.out.println(">>> EditServlet: 受信ID = " + idStr);
        
        // IDが送られてこなかった場合の簡易チェック
        if (idStr == null || idStr.isEmpty()) {
            System.out.println(">>> EditServlet: IDが空のためリダイレクトします");
            response.sendRedirect("AdminLineupServlet");
            return;
        }

        try {
            // 2. String型のIDをint型に変換
            int productId = Integer.parseInt(idStr);
            
            // 3. Serviceを介して、そのIDの商品情報を1件取得
            // ※ すでに ProductService に getProductById メソッドを作成済みである前提です
            ProductDTO product = productService.getProductById(productId);

            if (product != null) {
                System.out.println(">>> EditServlet: 商品が見つかりました！ 名前 = " + product.getProductName());
                // 4. 取得した商品データをリクエストスコープに保存
                // これにより、Edit.jsp側で ${product.productName} のように値を取り出せます
                request.setAttribute("product", product);
                
                // 5. 編集画面 (Edit.jsp) へフォワード
                // パスはご自身の環境に合わせて調整してください（例: /WEB-INF/views/Edit.jsp）
                request.getRequestDispatcher("/WEB-INF/views/Edit.jsp").forward(request, response);
                
            } else {
                System.out.println(">>> EditServlet: ID=" + productId + " に該当する商品がDAOMockで見つかりませんでした（null）");
                // 商品が見つからなかった場合は一覧へ戻す
                response.sendRedirect("AdminLineupServlet");
            }

        } catch (NumberFormatException e) {
            // IDが数値でない不正なリクエストが来た場合
            e.printStackTrace();
            response.sendRedirect("AdminLineupServlet");
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
            // 1. リクエストパラメータの取得
            int productId = Integer.parseInt(request.getParameter("productId"));
            String productName = request.getParameter("productName");
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            int price = Integer.parseInt(request.getParameter("price"));
            String productDescription = request.getParameter("productDescription");
            boolean isRecommended = "true".equals(request.getParameter("isRecommended"));

            // 2. 現在の登録情報を一度取得（画像パスを維持するため）
            ProductDTO currentProduct = productService.getProductById(productId);

            // 3. 画像の処理
            Part filePart = request.getPart("image");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String productImageUrl = currentProduct.getProductImageUrl(); // デフォルトは元の画像パス

            if (fileName != null && !fileName.isEmpty()) {
                // 新しい画像が選択されている場合のみ上書き（保存処理はRegisterと同様に必要）
                String uploadPath = getServletContext().getRealPath("/uploads");
                filePart.write(uploadPath + "/" + fileName);
                productImageUrl = "uploads/" + fileName;
            }

            // 4. DTOの組み立て
            ProductDTO product = new ProductDTO();
            product.setProductId(productId);
            product.setProductName(productName);
            product.setCategoryId(categoryId);
            product.setPrice(price);
            product.setProductDescription(productDescription);
            product.setProductImageUrl(productImageUrl);
            product.setFavorite(isRecommended);

            // 5. Service経由で更新実行
            productService.updateProduct(product);

            // 6. 完了ダイアログ用のフラグを付けて一覧へ戻る
            response.sendRedirect("AdminLineupServlet?status=updated");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "更新に失敗しました。内容を確認してください。");
            // 失敗した場合は編集画面に戻す
            doGet(request, response);
        }
    }
}


