package com.cimmino.shop.database.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ArriviDTO {

    private Long id;
    private LocalDate data;
    private BigDecimal peso_lordo;
	private BigDecimal peso_netto;
	private BigDecimal media;
    private Long merceId;
	private BigDecimal calo;
	private int freddo; 
	private String merceName;
    private List<BinsArriviDTO> bins;
	private String intestazione_merce;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Long getMerceId() {
		return merceId;
	}

	public void setMerceId(Long merceId) {
		this.merceId = merceId;
	}

	public List<BinsArriviDTO> getBins() {
		return bins;
	}

	public void setBins(List<BinsArriviDTO> bins) {
		this.bins = bins;
	}

	


	public int getFreddo() {
		return freddo;
	}

	public void setFreddo(int freddo) {
		this.freddo = freddo;
	}

	public String getMerceName() {
		return merceName;
	}

	public void setMerceName(String merceName) {
		this.merceName = merceName;
	}

	public BigDecimal getMedia() {
		return media;
	}

	public void setMedia(BigDecimal media) {
		this.media = media;
	}

	public void setPeso_lordo(BigDecimal peso_lordo) {
		this.peso_lordo = peso_lordo;
	}

	public void setPeso_netto(BigDecimal peso_netto) {
		this.peso_netto = peso_netto;
	}

	public BigDecimal getPeso_lordo() {
		return peso_lordo;
	}

	public BigDecimal getPeso_netto() {
		return peso_netto;
	}

	public BigDecimal getCalo() {
		return calo;
	}

	public void setCalo(BigDecimal calo) {
		this.calo = calo;
	}

	public String getIntestazione_merce() {
		return intestazione_merce;
	}

	public void setIntestazione_merce(String intestazione_merce) {
		this.intestazione_merce = intestazione_merce;
	}
}