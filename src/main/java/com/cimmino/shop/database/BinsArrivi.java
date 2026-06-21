package com.cimmino.shop.database;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "BinsArrivi")
public class BinsArrivi {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "binsarrivi_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "bin_id") // FK nella tabella bins
	private Bin bin;
	
	@Column(name = "peso_lordo")
	private BigDecimal pesoLordo;

	@Column(name = "peso_netto")
	private BigDecimal pesoNetto;
	
	private BigDecimal media;

	private Integer numBins;
	
	@ManyToOne
	@JoinColumn(name = "arrivo_id")
	private Arrivi arrivo;
	
	 public BinsArrivi() {} 
	 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Bin getBin() {
		return bin;
	}

	public void setBin(Bin bin) {
		this.bin = bin;
	}




	public Arrivi getArrivo() {
		return arrivo;
	}

	public void setArrivo(Arrivi arrivo) {
		this.arrivo = arrivo;
	}

	public Integer getNumBins() {
		return numBins;
	}

	public void setNumBins(Integer numBins) {
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

	public BigDecimal getMedia() {
		return media;
	}

	public void setMedia(BigDecimal media) {
		this.media = media;
	}



}
