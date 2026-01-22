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
import model.dto.OptionDTO;
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
            // 1. 画面からの入力値を取得
            String productIdStr = request.getParameter("productId");
            String quantityStr = request.getParameter("quantity");
            String[] optionIdStrs = request.getParameterValues("optionIds"); // チェックボックス

            if (productIdStr != null && quantityStr != null) {
                int productId = Integer.parseInt(productIdStr);
                int quantity = Integer.parseInt(quantityStr);
                
                // 2. 商品情報を取得
                ProductDTO product = customerService.getProductById(productId);
                
                // 3. 選択されたオプション情報をリスト化する
                List<OptionDTO> selectedOptions = new ArrayList<>();
                
                // チェックボックスが一つでも選ばれていれば処理
                if (optionIdStrs != null) {
                    // 全オプション情報を取得（ここからID一致を探す）
                    List<OptionDTO> allOptions = customerService.getOptionList();
                    
                    for (String idStr : optionIdStrs) {
                        int id = Integer.parseInt(idStr);
                        // IDが一致するOptionDTOを探してリストに追加
                        for (OptionDTO opt : allOptions) {
                            if (opt.getId() == id) { 
                                selectedOptions.add(opt);
                                break; 
                            }
                        }
                    }
                }
                
                if (product != null) {
                    HttpSession session = request.getSession();
                    @SuppressWarnings("unchecked")
                    List<CartItemDTO> cart = (List<CartItemDTO>) session.getAttribute("cart");
                    
                    if (cart == null) {
                        cart = new ArrayList<>();
                        session.setAttribute("cart", cart);
                    }
                    
                    // 4. カートアイテム作成
                    CartItemDTO newItem = new CartItemDTO(product, quantity);
                    // 【重要】検索したオプション情報(OptionDTOのリスト)をセット
                    newItem.setOptionList(selectedOptions);
                    
                    cart.add(newItem);
                }
            }
            
            // 成功画面へ
            request.getRequestDispatcher("/WEB-INF/views/add_success.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("MenuListServlet");
        }
    }
}