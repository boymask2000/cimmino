package com.cimmino.shop.database;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bin_id")
	private Bin bin;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "merce_id")
	private Merce merce;

	private BigDecimal lordo;
	private BigDecimal netto;
	private BigDecimal tara;

	private Integer nBins;

	private LocalDate data;

	private LocalDate dtt;

	private BigDecimal prezzo;
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

	public Merce getMerce() {
		return merce;
	}

	public void setMerce(Merce merce) {
		this.merce = merce;
	}

	public BigDecimal getLordo() {
		return lordo;
	}

	public void setLordo(BigDecimal lordo) {
		this.lordo = lordo;
	}

	public BigDecimal getNetto() {
		return netto;
	}

	public void setNetto(BigDecimal netto) {
		this.netto = netto;
	}

	public BigDecimal getTara() {
		return tara;
	}

	public void setTara(BigDecimal tara) {
		this.tara = tara;
	}

	public BigDecimal getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(BigDecimal prezzo) {
		this.prezzo = prezzo;
	}

	public BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

}
