package com.cimmino.shop.database.dto;

import java.math.BigDecimal;

public class VenditaDTO {

	 private Long id;

	    private Long commercianteId;
	    private Long binId;
	    private Long arrivoId;


	    private BigDecimal prezzo;
	    private BigDecimal scarto;
	    private BigDecimal importo;
	public Long getArrivoId() {
		return arrivoId;
	}
	public void setArrivoId(Long arrivoId) {
		this.arrivoId = arrivoId;
	}
	public Long getBinId() {
		return binId;
	}
	public void setBinId(Long binId) {
		this.binId = binId;
	}
	public Long getCommercianteId() {
		return commercianteId;
	}
	public void setCommercianteId(Long commercianteId) {
		this.commercianteId = commercianteId;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(BigDecimal prezzo) {
		this.prezzo = prezzo;
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
	
}