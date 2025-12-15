package model.dto;

import java.sql.Timestamp;

public class CategoryDTO {
	
	//DTOの属性
	private int categoryId;			//カテゴリーID
	private String categoryName;	//カテゴリー名
	private Timestamp createAt;			//作成時間
	private Timestamp updateAt;			//変更時間
	
	//各属性のgetter&setter
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Timestamp getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Timestamp createAt) {
		this.createAt = createAt;
	}
	public Timestamp getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(Timestamp updateAt) {
		this.updateAt = updateAt;
	}
	
	
}
