package model.dto;

import java.util.Date;

public class ProductDTO {
	
	// DTOの属性
	private int productId;	     		// 商品ID 				(DB: 商品ID)
	private int categoryId;	     		// カテゴリーID 		(DB: カテゴリーID)
	private String productName;  		// 商品名 				(DB: 商品名)
	private int price;           		// 価格 				(DB: 商品単価)
	private String productImageUrl; 	// 画像URL 				(DB: 商品画像)
	private boolean favorite;    		// おすすめ商品判定 	(DB: おすすめ選択)
	
	private Date createdAt;	     		// 作成時間 			(DB: 作成時間)
	private Date updateAt;	     		// 更新時間 			(DB: 更新時間)
	
	//各属性のgetter&setter
	//DAOでListに格納予定
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
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
	public String getProductImageUrl() {
		return productImageUrl;
	}
	public void setProductImageUrl(String productImageUrl) {
		this.productImageUrl = productImageUrl;
	}
	public boolean isFavorite() {
		return favorite;
	}
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
}
