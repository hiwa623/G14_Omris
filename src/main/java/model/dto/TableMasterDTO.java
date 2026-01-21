package model.dto;

import java.io.Serializable;

public class TableMasterDTO implements Serializable {
	private int id;
    private String tableNo;
    private int status; // 0:空席, 1:使用中
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTableNo() {
		return tableNo;
	}
	public void setTableNo(String tableNo) {
		this.tableNo = tableNo;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
