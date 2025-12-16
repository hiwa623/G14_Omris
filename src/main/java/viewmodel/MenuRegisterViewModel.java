package viewmodel;

/**
 * MenuRegisterViewModel
 * メニュー登録画面 (register.jsp) に渡すための状態やメッセージを保持するクラス。
 * 登録処理の結果（成功/失敗）とメッセージをViewに伝える役割を持つ。
 */
public class MenuRegisterViewModel {
	private boolean success;	//処理が成功したかどうか
	private String message;		//画面に表示するメッセージ
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
