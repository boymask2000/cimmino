package com.cimmino.shop.database;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "OpCommerciante")
public class OpCommerciante {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "op_id")
	private Long id;

	@ManyToOne
    @JoinColumn(name = "commerciante_id") // FK nella tabella bins
	private Commerciante commerciante;
	
	@ManyToOne
	@JoinColumn(name = "bin_id")
	private Bin bin;
	
	private Integer lordo;
	private Integer netto;
	private Integer tara;
	
	private Integer nBins;
	
	private LocalDate data;

	private LocalDate dtt;
	
	private BigDecimal importo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Commerciante getCommerciante() {
		return commerciante;
	}

	public void setCommerciante(Commerciante commerciante) {
		this.commerciante = commerciante;
	}

	public Bin getBin() {
		return bin;
	}

	public void setBin(Bin bin) {
		this.bin = bin;
	}

	public Integer getLordo() {
		return lordo;
	}

	public void setLordo(Integer lordo) {
		this.lordo = lordo;
	}

	public Integer getNetto() {
		return netto;
	}

	public void setNetto(Integer netto) {
		this.netto = netto;
	}

	public Integer getTara() {
		return tara;
	}

	public void setTara(Integer tara) {
		this.tara = tara;
	}

	public Integer getnBins() {
		return nBins;
	}

	public void setnBins(Integer nBins) {
		this.nBins = nBins;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public LocalDate getDtt() {
		return dtt;
	}

	public void setDtt(LocalDate dtt) {
		this.dtt = dtt;
	}

	public BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	
	
}
