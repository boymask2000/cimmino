package com.cimmino.shop.database;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
	private List<BinsArrivi> bins = new ArrayList<>();
	
	private int peso_lordo=0;
	private int peso_netto=0;
	private Integer tara;
	
	private LocalDate data;

	private LocalDate dtt;
	
	private BigDecimal media;
	
	private Double scarto;
	
	private Double prezzo;
	
	private Double importo;
	
	public Commerciante getCommerciante() {
		return commerciante;
	}
	public void setCommerciante(Commerciante commerciante) {
		this.commerciante = commerciante;
	}
	public Double getImporto() {
		return importo;
	}
	public void setImporto(double importo2) {
		this.importo = importo2;
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

	public Integer getTara() {
		return tara;
	}
	public void setTara(Integer tara) {
		this.tara = tara;
	}
	public Double getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(Double double1) {
		this.prezzo = double1;
	}
	public BigDecimal getMedia() {
		return media;
	}
	public void setMedia(BigDecimal media) {
		this.media = media;
	}
	public Double getScarto() {
		return scarto;
	}
	public void setScarto(Double double1) {
		this.scarto = double1;
	}
	public Arrivi getArrivo() {
		return arrivo;
	}
	public void setArrivo(Arrivi arrivo) {
		this.arrivo = arrivo;
	}
	public void setImporto(Double importo) {
		this.importo = importo;
	}
	public List<BinsArrivi> getBins() {
		return bins;
	}
	public void setBins(List<BinsArrivi> bins) {
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


	
}
