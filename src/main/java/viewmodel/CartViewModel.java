package viewmodel;

import java.util.List;

import model.dto.CartItemDTO;
import model.dto.OptionDTO; 

/**
 * CartViewModel
 * カート画面 (cart.jsp) の表示データを保持する。
 */
public class CartViewModel {
	private List<CartItemDTO> cartItems;
	private String errorMessage;

	// ゲッターとセッター
	public List<CartItemDTO> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItemDTO> cartItems) {
		this.cartItems = cartItems;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * 合計金額を計算するロジック（Viewで楽をするため）
	 * 商品単価 ＋ オプション料金 × 数量 で計算します。
	 */
	public int getTotalPrice() {
		int total = 0;

		// カートが空でなければ計算
		if (cartItems != null) {
			for (CartItemDTO item : cartItems) {
				// 1. 商品の基本価格
				int unitPrice = item.getProduct().getPrice();

				// 2. オプション料金を単価に足す
				if (item.getOptionList() != null) {
					for (OptionDTO opt : item.getOptionList()) {
						unitPrice += opt.getOptionPrice();
					}
				}

				// 3. (単価 + オプション) × 数量 を合計に加算
				total += unitPrice * item.getQuantity();
			}
		}
		return total;
	}
}