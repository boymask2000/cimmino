package com.cimmino.shop.database;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Vendite")
public class Vendite {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vendite_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "arrivo_id")
	private Arrivi arrivo;
	
	@ManyToOne
    @JoinColumn(name = "commerciante_id") // FK nella tabella bins
	private Commerciante commerciante;
	
	@OneToMany(mappedBy = "vendita",
	           cascade = CascadeType.ALL,
	           orphanRemoval = true)
	@JsonManagedReference
	private List<BinsVendite> bins = new ArrayList<>();
	
	private BigDecimal peso_lordo;
	private BigDecimal peso_netto;
	private BigDecimal tara;
	
	private LocalDate data;

	private LocalDate dtt;
	
	private BigDecimal media;
	
	private BigDecimal scarto;
	
	private BigDecimal prezzo;
	
	private BigDecimal importo;
	
	public Commerciante getCommerciante() {
		return commerciante;
	}
	public void setCommerciante(Commerciante commerciante) {
		this.commerciante = commerciante;
	}
	
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
	public LocalDate getDtt() {
		return dtt;
	}
	public void setDtt(LocalDate dtt) {
		this.dtt = dtt;
	}

	
	public BigDecimal getMedia() {
		return media;
	}
	public void setMedia(BigDecimal media) {
		this.media = media;
	}
	
	public Arrivi getArrivo() {
		return arrivo;
	}
	public void setArrivo(Arrivi arrivo) {
		this.arrivo = arrivo;
	}
	
	public List<BinsVendite> getBins() {
		return bins;
	}
	public void setBins(List<BinsVendite> bins) {
		this.bins = bins;
	}
	public BigDecimal getPeso_lordo() {
		return peso_lordo;
	}
	public void setPeso_lordo(BigDecimal peso_lordo) {
		this.peso_lordo = peso_lordo;
	}
	public BigDecimal getPeso_netto() {
		return peso_netto;
	}
	public void setPeso_netto(BigDecimal peso_netto) {
		this.peso_netto = peso_netto;
	}
	public BigDecimal getTara() {
		return tara;
	}
	public void setTara(BigDecimal tara) {
		this.tara = tara;
	}
	public BigDecimal getScarto() {
		return scarto;
	}
	public void setScarto(BigDecimal scarto) {
		this.scarto = scarto;
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
