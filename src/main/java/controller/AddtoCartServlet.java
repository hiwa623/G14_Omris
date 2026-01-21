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
import model.service.CustomerService;

@WebServlet("/AddtoCartServlet")
public class AddtoCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerService customerService = new CustomerService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        try {
            // 1. パラメータ取得
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            
            // チェックボックスは複数値なので getParameterValues で取得（選ばれてない場合はnull）
            String[] optionIdStrs = request.getParameterValues("optionIds");
            List<Integer> optionIds = new ArrayList<>();
            if (optionIdStrs != null) {
                for (String s : optionIdStrs) {
                    optionIds.add(Integer.parseInt(s));
                }
            }

            // 2. 商品情報取得
            ProductDTO product = customerService.getProductById(productId);
            
            if (product != null) {
                // 3. セッションカート取得
                HttpSession session = request.getSession();
                @SuppressWarnings("unchecked")
                List<CartItemDTO> cart = (List<CartItemDTO>) session.getAttribute("cart");
                if (cart == null) {
                    cart = new ArrayList<>();
                    session.setAttribute("cart", cart);
                }
                
                // 4. カートアイテム作成
                CartItemDTO newItem = new CartItemDTO(product, quantity);
                newItem.setOptionIds(optionIds); // オプションをセット
                
                // ★本来は「同じ商品で同じオプションなら合算」するロジックを入れると親切ですが
                // 簡易化のため、毎回別レコードとして追加します
                cart.add(newItem);
            }
            
            // 5. 【変更】選択画面（add_success.jsp）へフォワード
            request.getRequestDispatcher("/WEB-INF/views/add_success.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("menu");
        }
    }
}