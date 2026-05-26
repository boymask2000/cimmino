package com.cimmino.shop.database.dto;

public class MerceDTO {
    private Long merce_id;
    private String name;
	public Long getMerce_id() {
		return merce_id;
	}
	public void setMerce_id(Long merce_id) {
		this.merce_id = merce_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}