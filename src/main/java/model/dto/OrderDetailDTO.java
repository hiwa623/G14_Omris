package model.dto;

import java.io.Serializable;

public class OrderDetailDTO implements Serializable {
	private String productName; // 商品名（productテーブルから結合して取得）
	private int quantity; // 数量
	private int price; // 購入時の単価
	// DB管理用ID
	private int detailId; // PK (追加)
	private int orderId; // FK (追加)
	private int productId; // FK (追加)
	private String productStatusId; // ステータス (追加: "未調理"など)

	// Getter & Setter
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getDetailId() {
		return detailId;
	}

	public void setDetailId(int detailId) {
		this.detailId = detailId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductStatusId() {
		return productStatusId;
	}

	public void setProductStatusId(String productStatusId) {
		this.productStatusId = productStatusId;
	}

}
