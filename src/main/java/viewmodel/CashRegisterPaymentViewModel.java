package viewmodel;

import java.util.ArrayList;
import java.util.List;

import model.dto.BillItemDTO;

public class CashRegisterPaymentViewModel {

	private String tableNo;
	private int totalPrice;
	private List<BillItemDTO> billItems = new ArrayList<>(); // 明細リストを追加

	public CashRegisterPaymentViewModel() {
	}

	// Getter / Setter
	public String getTableNo() {
		return tableNo;
	}

	public void setTableNo(String tableNo) {
		this.tableNo = tableNo;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<BillItemDTO> getBillItems() {
		return billItems;
	}

	public void setBillItems(List<BillItemDTO> billItems) {
		this.billItems = billItems;
	}
}
