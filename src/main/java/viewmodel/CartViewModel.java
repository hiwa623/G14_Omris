package viewmodel;

import java.util.List;

import model.dto.CartItemDTO;


/**
 * CartViewModel
 * カート画面 (cart.jsp) の表示データを保持する。
 */
public class CartViewModel {
	private List<CartItemDTO> cartItems;
    private String errorMessage;

    // ゲッターとセッター
    public List<CartItemDTO> getCartItems() { return cartItems; }
    public void setCartItems(List<CartItemDTO> cartItems) { this.cartItems = cartItems; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    /**
     * 合計金額を計算するロジック（Viewで楽をするため）
     */
    public int getTotalPrice() {
        if (cartItems == null) return 0;
        return cartItems.stream()
                .mapToInt(i -> i.getProduct().getPrice() * i.getQuantity())
                .sum();
    }
}
