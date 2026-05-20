package com.cimmino.shop.database.dto;


public class BinDTO {
	private Long id;
    private String name;
    private Integer pesoLordo;
    private Integer tara;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Integer getTara() {
		return tara;
	}
	public void setTara(Integer tara) {
		this.tara = tara;
	}
	public Integer getPesoLordo() {
		return pesoLordo;
	}
	public void setPesoLordo(Integer pesoLordo) {
		this.pesoLordo = pesoLordo;
	}
}