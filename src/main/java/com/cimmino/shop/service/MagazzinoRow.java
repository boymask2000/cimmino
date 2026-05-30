package com.cimmino.shop.service;

import java.time.LocalDate;

public class MagazzinoRow {
	private LocalDate date;
	private String bin;
	private int num;
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
}
