package com.cimmino.shop.database.dto;

import java.time.LocalDate;
import java.util.List;

public class ArriviDTO {

    private Long id;
    private LocalDate data;
	private int peso_lordo;
	private int peso_netto;
    private Long merceId;
	private int calo;
	private int freddo; 
	private String merceName;
    private List<BinsArriviDTO> bins;

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

	public int getPeso_lordo() {
		return peso_lordo;
	}

	public void setPeso_lordo(int peso_lordo) {
		this.peso_lordo = peso_lordo;
	}

	public int getPeso_netto() {
		return peso_netto;
	}

	public void setPeso_netto(int peso_netto) {
		this.peso_netto = peso_netto;
	}

	public int getCalo() {
		return calo;
	}

	public void setCalo(int calo) {
		this.calo = calo;
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
}