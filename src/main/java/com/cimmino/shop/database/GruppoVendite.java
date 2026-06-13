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
import jakarta.persistence.Transient;

@Entity
@Table(name = "GruppoVendite")
public class GruppoVendite {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@OneToMany(mappedBy = "gruppoVendite")
	private List<Vendita> vendite;
	
	@OneToMany(mappedBy = "gruppoVendita", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<BinsGruppoVendita> bins = new ArrayList<>();
	
//	
	@ManyToOne
	@JoinColumn(name = "commerciante_id") // FK nella tabella bins
	private Commerciante commerciante;
//	
	private BigDecimal peso_lordo;
	private BigDecimal nettoDiTara; // netto di tara
	private BigDecimal nettoDiScarto;
	private BigDecimal tara;

	private LocalDate data;

	private String ddt;

	private BigDecimal media;

	private BigDecimal scarto;

	private BigDecimal prezzo;

	private BigDecimal importo;
	
	private BigDecimal pesoLordoTotale; 
	
	private String status;
	
	@Transient
	private Integer numeroTotaleBins;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Vendita> getVendite() {
		return vendite;
	}

	public void setVendite(List<Vendita> vendite) {
		this.vendite = vendite;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getPesoLordoTotale() {
		return pesoLordoTotale;
	}

	public void setPesoLordoTotale(BigDecimal pesoLordoTotale) {
		this.pesoLordoTotale = pesoLordoTotale;
	}

	

	public BigDecimal getPeso_lordo() {
		return peso_lordo;
	}

	public void setPeso_lordo(BigDecimal peso_lordo) {
		this.peso_lordo = peso_lordo;
	}

	public BigDecimal getNettoDiTara() {
		return nettoDiTara;
	}

	public void setNettoDiTara(BigDecimal nettoDiTara) {
		this.nettoDiTara = nettoDiTara;
	}

	public BigDecimal getNettoDiScarto() {
		return nettoDiScarto;
	}

	public void setNettoDiScarto(BigDecimal nettoDiScarto) {
		this.nettoDiScarto = nettoDiScarto;
	}

	public BigDecimal getTara() {
		return tara;
	}

	public void setTara(BigDecimal tara) {
		this.tara = tara;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getDdt() {
		return ddt;
	}

	public void setDdt(String ddt) {
		this.ddt = ddt;
	}

	public BigDecimal getMedia() {
		return media;
	}

	public void setMedia(BigDecimal media) {
		this.media = media;
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

	public List<BinsGruppoVendita> getBins() {
		return bins;
	}

	public void setBins(List<BinsGruppoVendita> bins) {
		this.bins = bins;
	}

	public Commerciante getCommerciante() {
		return commerciante;
	}

	public void setCommerciante(Commerciante commerciante) {
		this.commerciante = commerciante;
	}

	public Integer getNumeroTotaleBins() {
		return numeroTotaleBins;
	}

	public void setNumeroTotaleBins(Integer numeroTotaleBins) {
		this.numeroTotaleBins = numeroTotaleBins;
	}

	
}
