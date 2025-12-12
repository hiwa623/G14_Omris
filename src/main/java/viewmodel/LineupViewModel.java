package viewmodel;

import java.util.List;

import model.dto.ProductDTO;

/**
 * LineupViewModel
 * 商品一覧画面 (Lineup View) の表示に必要な全てのデータを集約するクラス。
 * Viewは、このクラスのデータのみを参照して画面を描画する。
 */
public class LineupViewModel {
	private List<ProductDTO> productList;
	
	private int totalItemCount; // 全体の件数（ページング処理などで利用）
    private String errorMessage; // エラーメッセージやシステム通知
    private boolean displayFavoriteOnly; // 「お気に入り」のみ表示するフラグ
    
    public List<ProductDTO> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductDTO> productList) {
        this.productList = productList;
    }

    // totalItemCount
    public int getTotalItemCount() {
        return totalItemCount;
    }

    public void setTotalItemCount(int totalItemCount) {
        this.totalItemCount = totalItemCount;
    }

    // errorMessage
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    // displayFavoriteOnly
    public boolean isDisplayFavoriteOnly() {
        return displayFavoriteOnly;
    }
    
    public void setDisplayFavoriteOnly(boolean displayFavoriteOnly) {
        this.displayFavoriteOnly = displayFavoriteOnly;
    }
}
