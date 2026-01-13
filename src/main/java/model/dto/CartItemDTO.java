package model.dto;

import java.io.Serializable;

public class CartItemDTO implements Serializable {
	private ProductDTO product;
    private int quantity;
    
    
    public CartItemDTO(ProductDTO product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
    
	public ProductDTO getProduct() {
		return product;
	}
	//setProductを作成しないのはユーザが注文した内容と異なる商品が保存される可能性をなくすため
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
