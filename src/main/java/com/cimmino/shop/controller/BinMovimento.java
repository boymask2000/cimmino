package com.cimmino.shop.controller;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.cimmino.shop.database.Bin;

public class BinMovimento {

	private Bin bin;

	private LocalDate data;
	private BigDecimal pesoLordo;

	private BigDecimal pesoNetto;

	private BigDecimal media;

	private Integer numBins;

	public Bin getBin() {
		return bin;
	}

	public void setBin(Bin bin) {
		this.bin = bin;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
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

	public BigDecimal getMedia() {
		return media;
	}

	public void setMedia(BigDecimal media) {
		this.media = media;
	}

	public Integer getNumBins() {
		return numBins;
	}

	public void setNumBins(Integer numBins) {
		this.numBins = numBins;
	}

}
