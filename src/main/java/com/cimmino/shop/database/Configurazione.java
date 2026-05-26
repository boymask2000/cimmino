package com.cimmino.shop.database;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Configurazione")
public class Configurazione {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "arrivo_id")
	private Long id;
	
	private BigDecimal prezzoFrigo ;
	
	private String installationId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getPrezzoFrigo() {
		return prezzoFrigo;
	}

	public void setPrezzoFrigo(BigDecimal prezzoFrigo) {
		this.prezzoFrigo = prezzoFrigo;
	}

	public String getInstallationId() {
		return installationId;
	}

	public void setInstallationId(String installationId) {
		this.installationId = installationId;
	}
}
