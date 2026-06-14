package com.cimmino.shop.service;

import java.time.LocalDate;

public class MagazzinoRow {
	private LocalDate date;
	private String sDate;
	private String bin;
	private int num;
	private String nomeMerce;
	
	private Long merceId;
	private Long binId;
	private Long arrivoId;
	
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getBin() {
		return bin;
	}
	public void setBin(String bin) {
		this.bin = bin;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getNomeMerce() {
		return nomeMerce;
	}
	public void setNomeMerce(String nomeMerce) {
		this.nomeMerce = nomeMerce;
	}
	public Long getMerceId() {
		return merceId;
	}
	public void setMerceId(Long merceId) {
		this.merceId = merceId;
	}
	public Long getBinId() {
		return binId;
	}
	public void setBinId(Long binId) {
		this.binId = binId;
	}
	public Long getArrivoId() {
		return arrivoId;
	}
	public void setArrivoId(Long arrivoId) {
		this.arrivoId = arrivoId;
	}
	public String getsDate() {
		int day = date.getDayOfMonth();
		int mese = date.getMonthValue();
		int anno = date.getYear();
		return day+"/"+mese+"/"+anno;
	}
	public void setsDate(String sDate) {
		this.sDate = sDate;
	}
}
