package com.vn.vietatech.model;

public class MemGrade {
	private String code;
	private String description;

	public MemGrade() {
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
