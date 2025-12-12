package model.dto;

import java.util.Date;

public class OptionDTO {

	// DTOの属性
	private int ID;				//オプションID
    private String optionName;	//オプション名
    private int price;			//オプション価格
    private int max;			//オプション条件
    
    private Date createdAt;		//作成時間
    private Date updatedAt;		//変更時間
    
  //各属性のgetter&setter
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getOptionName() {
		return optionName;
	}
	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
