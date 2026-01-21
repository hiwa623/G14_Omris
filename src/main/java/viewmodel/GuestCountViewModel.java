package viewmodel;

public class GuestCountViewModel {
	private String selectedTableNo; // 画面に「テーブル: A-1」のように出す用
    private String errorMessage;
	public String getSelectedTableNo() {
		return selectedTableNo;
	}
	public void setSelectedTableNo(String selectedTableNo) {
		this.selectedTableNo = selectedTableNo;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
    
}
