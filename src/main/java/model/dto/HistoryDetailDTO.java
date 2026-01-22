package model.dto;

import java.util.ArrayList;
import java.util.List;

public class HistoryDetailDTO {

	private String productName;
	private int price;
	private int quantity;
	private List<String> optionNames = new ArrayList<>();
	//ステータス用フィールド
	private String statusId; // 'NEW', 'COOKING', 'SERVED'
	private String statusName; // '未調理', '調理中', '提供済み'

	//getter&setter
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public List<String> getOptionNames() {
		return optionNames;
	}

	public void setOptionNames(List<String> optionNames) {
		this.optionNames = optionNames;
	}

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public void addOptionName(String name) {
		if (!this.optionNames.contains(name)) { // 重複チェックを追加
			this.optionNames.add(name);
		}
	}
}