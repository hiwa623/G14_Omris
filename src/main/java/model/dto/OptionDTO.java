package model.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class OptionDTO implements Serializable {

	// DTOの属性
	private int id;				//オプションID
    private String optionName;	//オプション名
    private int optionPrice;			//オプション価格
    private int optionLimit;			//オプション条件
    
    private Timestamp createdAt;		//作成時間
    private Timestamp updatedAt;		//変更時間
    
    //各属性のgetter&setter
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOptionName() {
		return optionName;
	}
	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}
	public int getOptionPrice() {
		return optionPrice;
	}
	public void setOptionPrice(int optionPrice) {
		this.optionPrice = optionPrice;
	}
	public int getOptionLimit() {
		return optionLimit;
	}
	public void setOptionLimit(int optionLimit) {
		this.optionLimit = optionLimit;
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
