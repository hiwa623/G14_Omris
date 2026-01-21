package model.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class LoginDTO implements Serializable{
	private int id;
    private String loginId;
    private String password; // セキュリティ上、Service層以外での取扱いに注意
    private String role;
    private Timestamp createdAt;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
}
