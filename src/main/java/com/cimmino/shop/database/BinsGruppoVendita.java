package com.cimmino.shop.database;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "BinsGruppoVendita")
public class BinsGruppoVendita {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "merce_id") 
	private Merce merce;
	
	@ManyToOne
	@JoinColumn(name = "bin_id") // FK nella tabella bins
	private Bin bin;
	
	@ManyToOne
	@JoinColumn(name = "gruppoVendita_id")
	private GruppoVendite gruppoVendita;
	private Boolean nostraProprieta;

	private int numBins;
	private BigDecimal prezzo;
	private BigDecimal pesoLordo;
	private BigDecimal pesoNetto;
	private String arriviSelect;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Merce getMerce() {
		return merce;
	}
	public void setMerce(Merce merce) {
		this.merce = merce;
	}
	public GruppoVendite getGruppoVendita() {
		return gruppoVendita;
	}
	public void setGruppoVendita(GruppoVendite gruppoVendita) {
		this.gruppoVendita = gruppoVendita;
	}
	
	public int getNumBins() {
		return numBins;
	}
	public void setNumBins(int numBins) {
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
	public String getArriviSelect() {
		return arriviSelect;
	}
	public void setArriviSelect(String arriviSelect) {
		this.arriviSelect = arriviSelect;
	}
	public Bin getBin() {
		return bin;
	}
	public void setBin(Bin bin) {
		this.bin = bin;
	}
	public Boolean getNostraProprieta() {
		return nostraProprieta;
	}
	public void setNostraProprieta(Boolean nostraProprieta) {
		this.nostraProprieta = nostraProprieta;
	}
	public BigDecimal getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(BigDecimal prezzo) {
		this.prezzo = prezzo;
	}
}
