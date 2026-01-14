package model.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class OrderHistoryDTO implements Serializable{
	private int orderId;
	private Timestamp orderDate;
	private int totalPrice;
	// この注文に紐づく明細リストを持たせる（1対多の構造）
	private List<OrderDetailDTO> details;

	// Getter & Setter
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Timestamp getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<OrderDetailDTO> getDetails() {
		return details;
	}

	public void setDetails(List<OrderDetailDTO> details) {
		this.details = details;
	}
}
