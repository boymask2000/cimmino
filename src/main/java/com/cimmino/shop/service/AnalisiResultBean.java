package com.cimmino.shop.service;

import java.math.BigDecimal;
import java.util.List;

import com.cimmino.shop.database.Vendite;
import com.cimmino.shop.database.dto.ArriviDTO;

public class AnalisiResultBean {
	private ArriviDTO arrivo;

	private List<Vendite> vendite;
	
	private BigDecimal totPesoLordoArrivo;
	private BigDecimal totPesoNettoArrivo;
	
	private BigDecimal totPesoLordoVendite;
	private BigDecimal totPesoNettoVendite;
	private BigDecimal totNettoDiTaraVendite ;
	
	public ArriviDTO getArrivo() {
		return arrivo;
	}
	public void setArrivo(ArriviDTO arrivo) {
		this.arrivo = arrivo;
	}
	public List<Vendite> getVendite() {
		return vendite;
	}
	public void setVendite(List<Vendite> vendite) {
		this.vendite = vendite;
	}
	public BigDecimal getTotPesoLordoArrivo() {
		return totPesoLordoArrivo;
	}
	public void setTotPesoLordoArrivo(BigDecimal totPesoLordoArrivo) {
		this.totPesoLordoArrivo = totPesoLordoArrivo;
	}
	public BigDecimal getTotPesoNettoArrivo() {
		return totPesoNettoArrivo;
	}
	public void setTotPesoNettoArrivo(BigDecimal totPesoNettoArrivo) {
		this.totPesoNettoArrivo = totPesoNettoArrivo;
	}
	public BigDecimal getTotPesoLordoVendite() {
		return totPesoLordoVendite;
	}
	public void setTotPesoLordoVendite(BigDecimal totPesoLordoVendite) {
		this.totPesoLordoVendite = totPesoLordoVendite;
	}
	public BigDecimal getTotPesoNettoVendite() {
		return totPesoNettoVendite;
	}
	public void setTotPesoNettoVendite(BigDecimal totPesoNettoVendite) {
		this.totPesoNettoVendite = totPesoNettoVendite;
	}
	public BigDecimal getTotNettoDiTaraVendite() {
		return totNettoDiTaraVendite;
	}
	public void setTotNettoDiTaraVendite(BigDecimal totNettoDiTaraVendite) {
		this.totNettoDiTaraVendite = totNettoDiTaraVendite;
	}


}
