package com.cimmino.shop;

import com.cimmino.shop.database.Commerciante;

public class CommerciantiDDTInfo {
	private Commerciante commerciante;
	private int numVenditeNoDDT;

	public Commerciante getCommerciante() {
		return commerciante;
	}

	public void setCommerciante(Commerciante commerciante) {
		this.commerciante = commerciante;
	}

	public int getNumVenditeNoDDT() {
		return numVenditeNoDDT;
	}

	public void setNumVenditeNoDDT(int numVenditeNoDDT) {
		this.numVenditeNoDDT = numVenditeNoDDT;
	}
}
