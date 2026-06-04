package com.cimmino.shop.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Titolare")
public class Titolare {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "commerciante_id")
	private Long id;
	
	private String name;
	private String indirizzo;
	private String pec;
	private String codFiscale;
	private String pIva;
	private String ggn;


	public Titolare() {
		// TODO Auto-generated constructor stub
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



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getGgn() {
		return ggn;
	}



	public void setGgn(String ggn) {
		this.ggn = ggn;
	}
}
