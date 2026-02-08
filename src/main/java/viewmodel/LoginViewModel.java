package viewmodel;

public class LoginViewModel {
	private String errorMessage;
    private String loginId; // 入力エラー時にIDを残しておく用

    public LoginViewModel() {}

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public String getLoginId() { return loginId; }
    public void setLoginId(String loginId) { this.loginId = loginId; }
}
