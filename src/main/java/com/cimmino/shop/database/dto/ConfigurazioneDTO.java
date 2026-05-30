package com.cimmino.shop.database.dto;

import java.math.BigDecimal;


public class ConfigurazioneDTO {

	private Long id;
	
	private String name;
	private String indirizzo;
	private String pec;
	private String codFiscale;
	private String pIva;
	
	private BigDecimal prezzoFrigoxCaldo ;
	private BigDecimal prezzoFrigoxFreddo ;
	
	private String installationId;
	
	private String ruolo;
	
	private String ggn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public String getInstallationId() {
		return installationId;
	}

	public void setInstallationId(String installationId) {
		this.installationId = installationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getPec() {
		return pec;
	}

	public void setPec(String pec) {
		this.pec = pec;
	}

	public String getCodFiscale() {
		return codFiscale;
	}

	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}

	public String getpIva() {
		return pIva;
	}

	public void setpIva(String pIva) {
		this.pIva = pIva;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getGgn() {
		return ggn;
	}

	public void setGgn(String ggn) {
		this.ggn = ggn;
	}

	public BigDecimal getPrezzoFrigoxCaldo() {
		return prezzoFrigoxCaldo;
	}

	public void setPrezzoFrigoxCaldo(BigDecimal prezzoFrigoxCaldo) {
		this.prezzoFrigoxCaldo = prezzoFrigoxCaldo;
	}

	public BigDecimal getPrezzoFrigoxFreddo() {
		return prezzoFrigoxFreddo;
	}

	public void setPrezzoFrigoxFreddo(BigDecimal prezzoFrigoxFreddo) {
		this.prezzoFrigoxFreddo = prezzoFrigoxFreddo;
	}
}
