package com.cimmino.shop.database;

public class VenditaForm {

    private Long arrivoId;
    private Long binId;
    private Long commercianteId;

    private Integer nBins;
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
	public Integer getnBins() {
		return nBins;
	}
	public void setnBins(Integer nBins) {
		this.nBins = nBins;
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
	public Double getImporto() {
		return importo;
	}
	public void setImporto(Double importo) {
		this.importo = importo;
	}
}