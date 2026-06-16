package com.cimmino.shop.database.dto;

import java.math.BigDecimal;

public class BinsGruppoVenditaDTO {
	private Long binId;
	private int numBins;
	private BigDecimal pesoLordo;
	private BigDecimal pesoNetto;
	private String arriviSelect;
	private Long commerciante;
	private Boolean nostraProprieta ;
	private BigDecimal scarto;
	private BigDecimal prezzo;
	private BigDecimal importo;
	
	
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
	public Boolean getNostraProprieta() {
		return nostraProprieta;
	}
	public void setNostraProprieta(Boolean nostraProprieta) {
		this.nostraProprieta = nostraProprieta;
	}
	public BigDecimal getScarto() {
		return scarto;
	}
	public void setScarto(BigDecimal scarto) {
		this.scarto = scarto;
	}
	public BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	public BigDecimal getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(BigDecimal prezzo) {
		this.prezzo = prezzo;
	}
	
}
