package com.cimmino.shop.database;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

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
	
	private int peso_lordo;
	private int peso_netto;
	
	private int calo;
	private int freddo; // 1=freddo 0=caldo
	
	private String intestazione_merce;
	
	@OneToMany(mappedBy = "arrivoEntity",
	           cascade = CascadeType.ALL,
	           orphanRemoval = true)
	private List<BinsArrivi> bins = new ArrayList<>();

	@OneToMany(mappedBy = "arrivoEntity")
	private Set<Vendite> vendite;
	
	

	public List<BinsArrivi> getBins() {
		return bins;
	}

	public void setBins(List<BinsArrivi> bins) {
		this.bins = bins;
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

	public int getCalo() {
		return calo;
	}

	public void setCalo(int calo) {
		this.calo = calo;
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



	public Set<Vendite> getVendite() {
		return vendite;
	}

	public void setVendite(Set<Vendite> vendite) {
		this.vendite = vendite;
	}

	public Merce getMerce() {
		return merce;
	}

	public void setMerce(Merce merce) {
		this.merce = merce;
	}
}
