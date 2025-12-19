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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 1. 削除する商品IDを取得
        String idStr = request.getParameter("productId");
        if (idStr != null && !idStr.isEmpty()) {
            int productId = Integer.parseInt(idStr);
            
            // 2. Service経由で削除実行
            productService.deleteProduct(productId);
            
            // 3. 一覧画面（AdminLineupServlet）へリダイレクト
            // その際、URLの末尾に「削除完了」の目印を付ける
            response.sendRedirect("AdminLineupServlet?status=deleted");
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
