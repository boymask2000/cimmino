package com.cimmino.shop.database.dto;

public class BinsArriviDTO {
	  private Long id;

	    private Long binId;
	    private String binName;

	    private Integer numBins;

	    private Integer pesoLordo;
	    private Integer pesoNetto;
	public Long getBinId() {
		return binId;
	}
	public void setBinId(Long binId) {
		this.binId = binId;
	}
	public String getBinName() {
		return binName;
	}
	public void setBinName(String binName) {
		this.binName = binName;
	}
	public Integer getNumBins() {
		return numBins;
	}
	public void setNumBins(Integer numBins) {
		this.numBins = numBins;
	}
	public Integer getPesoLordo() {
		return pesoLordo;
	}
	public void setPesoLordo(Integer pesoLordo) {
		this.pesoLordo = pesoLordo;
	}
	public Integer getPesoNetto() {
		return pesoNetto;
	}
	public void setPesoNetto(Integer pesoNetto) {
		this.pesoNetto = pesoNetto;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}