package model.dto;

import java.io.Serializable;

public class ProductStatusDTO implements Serializable {
	private String id; // '未調理' など
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
