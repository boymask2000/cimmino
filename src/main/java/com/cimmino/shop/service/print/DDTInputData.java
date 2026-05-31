package com.cimmino.shop.service.print;

import com.cimmino.shop.database.Trasportatore;

public class DDTInputData {
	private String trasportoAmezzo; // vettore, cedente, concessionario
	private String luogoDiDestinazione = "IDEM"; // default IDEM
	private String categoria;
	private String aspettoEsteriore = "BINS IN PLASTICA";
	private Trasportatore trasportatore;
	
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
	public Trasportatore getTrasportatore() {
		return trasportatore;
	}
	public void setTrasportatore(Trasportatore trasportatore) {
		this.trasportatore = trasportatore;
	}

}
