package com.cimmino.shop.database;



import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;

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
@Table(name = "Arrivi")
public class Arrivi {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "arrivo_id")
	private Long id;


	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate data;
	
	@ManyToOne
    @JoinColumn(name = "merce_id") 
	private Merce merce;
	
	private BigDecimal peso_lordo;
	private BigDecimal peso_netto;
	private BigDecimal media;
	
	private BigDecimal calo;
	private int freddo; // 1=freddo 0=caldo
	
	private String intestazione_merce;
	
	@OneToMany(mappedBy = "arrivo",
	           cascade = CascadeType.ALL,
	           orphanRemoval = true)
	@JsonManagedReference
	private List<BinsArrivi> bins = new ArrayList<>();

	@OneToMany(mappedBy = "arrivo")
	private List<Vendite> vendite;
	
	@Transient
	private Map<String, BigDecimal> sums=new HashMap<String, BigDecimal>();

	public List<BinsArrivi> getBins() {
		return bins;
	}


	public void setBins(List<BinsArrivi> bins) {
	    this.bins.clear();

	    if (bins != null) {
	        for (BinsArrivi b : bins) {
	            b.setArrivo(this);
	            this.bins.add(b);
	        }
	    }
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

	

	
	public int getFreddo() {
		return freddo;
	}

	public void setFreddo(int freddo) {
		this.freddo = freddo;
	}

	public String getIntestazione_merce() {
		return intestazione_merce;
	}

	public void setIntestazione_merce(String intestazione_merce) {
		this.intestazione_merce = intestazione_merce;
	}




	public Merce getMerce() {
		return merce;
	}

	public void setMerce(Merce merce) {
		this.merce = merce;
	}

	public List<Vendite> getVendite() {
		 vendite.sort(Comparator.comparing(Vendite::getData));
		return vendite;
	}

	public void setVendite(List<Vendite> vendite) {
		this.vendite = vendite;
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


	public BigDecimal getCalo() {
		return calo;
	}


	public void setCalo(BigDecimal calo) {
		this.calo = calo;
	}


	public Map<String, BigDecimal> getSums() {
		return sums;
	}


	public void setSums(Map<String, BigDecimal> sums) {
		this.sums = sums;
	}


	public BigDecimal getMedia() {
		return media;
	}


	public void setMedia(BigDecimal media) {
		this.media = media;
	}



}
