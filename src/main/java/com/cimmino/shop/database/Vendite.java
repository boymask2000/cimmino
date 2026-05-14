package com.cimmino.shop.database;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Vendite")
public class Vendite {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vendite_id")
	private Long id;

	private Long arrivo_entity;
	
	@ManyToOne
    @JoinColumn(name = "vendita_id") // FK nella tabella bins
	private Commerciante commerciante;
	
	private String nomeBin;
	private int nBins;
	
	private Date data;
	private Date dtt;
	
	private BigDecimal importo;
	
	public Commerciante getCommerciante() {
		return commerciante;
	}
	public void setCommerciante(Commerciante commerciante) {
		this.commerciante = commerciante;
	}
	public BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getArrivo_entity() {
		return arrivo_entity;
	}
	public void setArrivo_entity(Long arrivo_entity) {
		this.arrivo_entity = arrivo_entity;
	}

	public String getNomeBin() {
		return nomeBin;
	}
	public void setNomeBin(String nomeBin) {
		this.nomeBin = nomeBin;
	}
	public int getnBins() {
		return nBins;
	}
	public void setnBins(int nBins) {
		this.nBins = nBins;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Date getDtt() {
		return dtt;
	}
	public void setDtt(Date dtt) {
		this.dtt = dtt;
	}
	
}
