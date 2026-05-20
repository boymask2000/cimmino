package com.cimmino.shop.database;

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
	private Integer pesoLordo;

	@Column(name = "peso_netto")
	private Integer pesoNetto;

	private Integer numBins;
	
	@Column(name = "numBinSold")
	private Integer numBinSold;

	@ManyToOne
	@JoinColumn(name = "vendite_id")
	private Vendite vendita;
	
	@ManyToOne
	@JoinColumn(name = "arrivo_id")
	private Arrivi arrivo;

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



	public Integer getPesoLordo() {
		return pesoLordo;
	}

	public void setPesoLordo(Integer pesoLordo) {
		this.pesoLordo = pesoLordo;
	}

	public Integer getPesoNetto() {
		return pesoNetto;
	}

	public void setPesoNetto(Integer pesoNetto) {
		this.pesoNetto = pesoNetto;
	}

	public Integer getNumBinSold() {
		return numBinSold;
	}

	public void setNumBinSold(Integer numBinSold) {
		this.numBinSold = numBinSold;
	}

	public Arrivi getArrivo() {
		return arrivo;
	}

	public void setArrivo(Arrivi arrivo) {
		this.arrivo = arrivo;
	}

	public Vendite getVendita() {
		return vendita;
	}

	public void setVendita(Vendite vendita) {
		this.vendita = vendita;
	}

	public Integer getNumBins() {
		return numBins;
	}

	public void setNumBins(Integer numBins) {
		this.numBins = numBins;
	}



}
