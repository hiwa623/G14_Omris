package viewmodel;

/**
 * OrderViewModel
 * 注文完了画面 (thanks.jsp) に表示する情報を保持する
 */
public class OrderViewModel {
	private boolean success;
    private String message;

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
