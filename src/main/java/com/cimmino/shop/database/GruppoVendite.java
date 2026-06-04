package com.cimmino.shop.database;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "GruppoVendite")
public class GruppoVendite {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@OneToMany(mappedBy = "gruppoVendite")
	private List<Vendita> vendite;
	
//	
	private Long commercianteId;
//	
//	private long merceId;
	
	private BigDecimal pesoLordoTotale; 
	
	private String status;

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

	public Long getCommercianteId() {
		return commercianteId;
	}

	public void setCommercianteId(Long commercianteId) {
		this.commercianteId = commercianteId;
	}

	
}
