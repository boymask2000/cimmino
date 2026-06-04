package com.cimmino.shop.service.print;

import java.util.List;

public class DDTInputData {
	private Long titolareId;
	private String numeroDDT;
	private String trasportoAmezzo; // vettore, cedente, concessionario
	private String luogoDiDestinazione = "IDEM"; // default IDEM
	private String categoria = "ove richiesta";
	private String aspettoEsteriore = "BINS IN PLASTICA";
	private Long trasportatore1Id;
	private Long trasportatore2Id;
	
	private List<Long> ids;
	private long commercianteId;
	
	public String getTrasportoAmezzo() {
		return trasportoAmezzo;
	}
	public void setTrasportoAmezzo(String trasportoAmezzo) {
		this.trasportoAmezzo = trasportoAmezzo;
	}
	public String getLuogoDiDestinazione() {
		return luogoDiDestinazione;
	}
	public void setLuogoDiDestinazione(String luogoDiDestinazione) {
		this.luogoDiDestinazione = luogoDiDestinazione;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getAspettoEsteriore() {
		return aspettoEsteriore;
	}
	public void setAspettoEsteriore(String aspettoEsteriore) {
		this.aspettoEsteriore = aspettoEsteriore;
	}

	public List<Long> getIds() {
		return ids;
	}
	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
	public long getCommercianteId() {
		return commercianteId;
	}
	public void setCommercianteId(long commercianteId) {
		this.commercianteId = commercianteId;
	}
	public Long getTrasportatore1Id() {
		return trasportatore1Id;
	}
	public void setTrasportatore1Id(Long trasportatore1Id) {
		this.trasportatore1Id = trasportatore1Id;
	}
	public Long getTrasportatore2Id() {
		return trasportatore2Id;
	}
	public void setTrasportatore2Id(Long trasportatore2Id) {
		this.trasportatore2Id = trasportatore2Id;
	}
	public Long getTitolareId() {
		return titolareId;
	}
	public void setTitolareId(Long titolareId) {
		this.titolareId = titolareId;
	}
	public String getNumeroDDT() {
		return numeroDDT;
	}
	public void setNumeroDDT(String numeroDDT) {
		this.numeroDDT = numeroDDT;
	}


}
