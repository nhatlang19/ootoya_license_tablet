package com.vn.vietatech.model;

public class Promo {
	private String code;
	private String description;

	public Promo() {
		code = "";
		description = "";
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code.trim();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description.trim();
	}

}
