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
@Table(name = "Vendite")
public class Vendita {
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

	@OneToMany(mappedBy = "vendita", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<BinsVendite> bins = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name = "gruppo_id") 
	private GruppoVendite gruppoVendite;
	
	private Boolean isMasterGruppo;


	@Transient
	private Integer numeroTotaleBins;

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
	
	private BigDecimal frigo;
	
	private String key;

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

	public void setNumeroTotaleBins(Integer numeroTotaleBins) {
		this.numeroTotaleBins = numeroTotaleBins;
	}

	public BigDecimal getFrigo() {
		return frigo;
	}

	public void setFrigo(BigDecimal frigo) {
		this.frigo = frigo;
	}

	public String getDdt() {
		return ddt;
	}

	public void setDdt(String ddt) {
		this.ddt = ddt;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getNumeroTotaleBins() {
		return numeroTotaleBins;
	}

	public GruppoVendite getGruppoVendite() {
		return gruppoVendite;
	}

	public void setGruppoVendite(GruppoVendite gruppoVendite) {
		this.gruppoVendite = gruppoVendite;
	}

	public Boolean getIsMasterGruppo() {
		return isMasterGruppo;
	}

	public void setIsMasterGruppo(Boolean isMasterGruppo) {
		this.isMasterGruppo = isMasterGruppo;
	}

}
