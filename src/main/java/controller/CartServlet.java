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
import viewmodel.CartViewModel;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. セッションからカートを取得
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        List<CartItemDTO> cart = (List<CartItemDTO>) session.getAttribute("cart");
        
     // ViewModelの作成
        CartViewModel vm = new CartViewModel();
        vm.setCartItems(cart);

        // ViewModel だけをリクエストスコープに入れる
        request.setAttribute("vm", vm);
        
        // 4. カート画面へフォワード
        request.getRequestDispatcher("/WEB-INF/views/cart_list.jsp").forward(request, response);
    }
}