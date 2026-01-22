package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dto.CartItemDTO;

/**
 * Servlet implementation class UpdateCartServlet
 */
@WebServlet("/UpdateCartServlet")
public class UpdateCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

        try {
            // 1. パラメータの取得
            String action = request.getParameter("action"); // "update" or "delete"
            int index = Integer.parseInt(request.getParameter("index")); // リストの何番目か
            
            // 2. セッションからカートを取得
            HttpSession session = request.getSession();
            @SuppressWarnings("unchecked")
            List<CartItemDTO> cart = (List<CartItemDTO>) session.getAttribute("cart");

            if (cart != null && index >= 0 && index < cart.size()) {
                
                if ("delete".equals(action)) {
                    // --- 削除処理 ---
                    cart.remove(index);
                    
                } else if ("update".equals(action)) {
                    // --- 数量変更処理 ---
                    int quantity = Integer.parseInt(request.getParameter("quantity"));
                    if (quantity > 0) {
                        CartItemDTO item = cart.get(index);
                        item.setQuantity(quantity);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 3. 処理が終わったらカート画面に戻る（再計算される）
        response.sendRedirect("CartServlet");
	}

}
