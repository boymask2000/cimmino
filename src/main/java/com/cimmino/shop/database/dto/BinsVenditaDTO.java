package com.cimmino.shop.database.dto;

import java.math.BigDecimal;

public class BinsVenditaDTO {
	  private Long id;

	    private Long binId;
	    private String binName;

	    private Integer numBins;

	    private BigDecimal pesoLordo;
	    private BigDecimal pesoNetto;
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
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getPesoLordo() {
		return pesoLordo;
	}
	public void setPesoLordo(BigDecimal pesoLordo) {
		this.pesoLordo = pesoLordo;
	}
	public BigDecimal getPesoNetto() {
		return pesoNetto;
	}
	public void setPesoNetto(BigDecimal pesoNetto) {
		this.pesoNetto = pesoNetto;
	}
}