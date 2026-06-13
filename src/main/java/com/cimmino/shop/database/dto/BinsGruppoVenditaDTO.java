package com.cimmino.shop.database.dto;

import java.math.BigDecimal;

public class BinsGruppoVenditaDTO {
	private Long binId;
	private int numBins;
	private BigDecimal pesoLordo;
	private BigDecimal pesoNetto;
	private String arriviSelect;
	private Long commerciante;
	public Long getBinId() {
		return binId;
	}
	public void setBinId(Long binId) {
		this.binId = binId;
	}
	public int getNumBins() {
		return numBins;
	}
	public void setNumBins(int numBins) {
		this.numBins = numBins;
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
	public String getArriviSelect() {
		return arriviSelect;
	}
	public void setArriviSelect(String arriviSelect) {
		this.arriviSelect = arriviSelect;
	}
	public Long getCommerciante() {
		return commerciante;
	}
	public void setCommerciante(Long commerciante) {
		this.commerciante = commerciante;
	}
}
