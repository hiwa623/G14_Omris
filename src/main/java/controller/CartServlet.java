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
import model.dto.OptionDTO;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. セッションからカートを取得
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        List<CartItemDTO> cart = (List<CartItemDTO>) session.getAttribute("cart");
        
        // 2. 合計金額を計算
        int totalAmount = 0;
        if (cart != null) {
            for (CartItemDTO item : cart) {
                // 商品の小計 (単価 * 数量)
                int subTotal = item.getProduct().getPrice() * item.getQuantity();
                
                // オプション料金の加算 (オプション単価 * 数量 と仮定)
                // CartItemDTOにオプションリスト(List<OptionDTO>)が入っている前提です
                if (item.getOptionList() != null) {
                    for (OptionDTO opt : item.getOptionList()) {
                        subTotal += opt.getOptionPrice() * item.getQuantity();
                    }
                }
                
                totalAmount += subTotal;
            }
        }
        
        // 3. JSPに渡す（カートリストはセッションにあるので、合計金額だけ渡せばOK）
        request.setAttribute("totalAmount", totalAmount);
        
        // 4. カート画面へフォワード
        request.getRequestDispatcher("/WEB-INF/views/cart_list.jsp").forward(request, response);
    }
}