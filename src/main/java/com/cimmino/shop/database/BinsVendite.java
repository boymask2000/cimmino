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
@Table(name = "BinsVendite")
public class BinsVendite {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "binvendite_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "bin_id") // FK nella tabella bins
	private Bin bin;

	@Column(name = "peso_lordo")
	private BigDecimal pesoLordo;

	@Column(name = "peso_netto")
	private BigDecimal pesoNetto;

	private Integer numBins;
	
	private Boolean nostraProprieta;

	@ManyToOne
	@JoinColumn(name = "vendite_id")
	private Vendita vendita;

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

	

	public Vendita getVendita() {
		return vendita;
	}

	public void setVendita(Vendita vendita) {
		this.vendita = vendita;
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

	public Boolean getNostraProprieta() {
		return nostraProprieta;
	}

	public void setNostraProprieta(Boolean nostraProprieta) {
		this.nostraProprieta = nostraProprieta;
	}

}
