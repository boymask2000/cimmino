package com.cimmino.shop.database.dto;

public class VenditaDTO {

	 private Long id;

	    private Long commercianteId;
	    private Long binId;
	    private Long arrivoId;


	    private Double prezzo;
	    private Double scarto;
	    private Double importo;
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

	public Double getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(Double prezzo) {
		this.prezzo = prezzo;
	}
	public Double getScarto() {
		return scarto;
	}
	public void setScarto(Double scarto) {
		this.scarto = scarto;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getImporto() {
		return importo;
	}
	public void setImporto(Double importo) {
		this.importo = importo;
	}
}