package model.dto;

public class BillItemDTO {
	private String productName;
	private int price;
	private int quantity;

	public BillItemDTO(String productName, int price, int quantity) {
		this.productName = productName;
		this.price = price;
		this.quantity = quantity;
	}

	// Getter
	public String getProductName() {
		return productName;
	}

	public int getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public int getSubTotal() {
		return price * quantity;
	}
}
