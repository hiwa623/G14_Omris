package model.dto;

import java.sql.Timestamp;

public class OptionDTO {

	// DTOの属性
	private int ID;				//オプションID
    private String optionName;	//オプション名
    private int price;			//オプション価格
    private int max;			//オプション条件
    
    private Timestamp createdAt;		//作成時間
    private Timestamp updatedAt;		//変更時間
    
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
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public Timestamp getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	
}
