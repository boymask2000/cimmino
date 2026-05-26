package com.cimmino.shop.database.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class VenditaDTO {

	 private Long id;

	    private Long commercianteId;
	    private Long binId;
	    private Long arrivoId;

	    private BigDecimal peso_lordo;
		private BigDecimal nettoDiTara; // netto di tara
		private BigDecimal nettoDiScarto;
		private BigDecimal tara;
	    private LocalDate data;

		private String dtt;

		private BigDecimal media;

		private BigDecimal scarto;

		private BigDecimal prezzo;

		private BigDecimal importo;
		
		private BigDecimal frigo;
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
	public BigDecimal getPeso_lordo() {
		return peso_lordo;
	}
	public void setPeso_lordo(BigDecimal peso_lordo) {
		this.peso_lordo = peso_lordo;
	}
	public BigDecimal getNettoDiTara() {
		return nettoDiTara;
	}
	public void setNettoDiTara(BigDecimal nettoDiTara) {
		this.nettoDiTara = nettoDiTara;
	}
	public BigDecimal getNettoDiScarto() {
		return nettoDiScarto;
	}
	public void setNettoDiScarto(BigDecimal nettoDiScarto) {
		this.nettoDiScarto = nettoDiScarto;
	}
	public BigDecimal getTara() {
		return tara;
	}
	public void setTara(BigDecimal tara) {
		this.tara = tara;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public String getDtt() {
		return dtt;
	}
	public void setDtt(String dtt) {
		this.dtt = dtt;
	}
	public BigDecimal getMedia() {
		return media;
	}
	public void setMedia(BigDecimal media) {
		this.media = media;
	}
	public BigDecimal getFrigo() {
		return frigo;
	}
	public void setFrigo(BigDecimal frigo) {
		this.frigo = frigo;
	}
	
}