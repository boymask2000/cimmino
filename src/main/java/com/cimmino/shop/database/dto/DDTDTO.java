package com.cimmino.shop.database.dto;

import java.time.LocalDate;

import jakarta.persistence.Column;


public class DDTDTO {

	private Long id;
	private String numeroDDT;
	private LocalDate date;
	
	@Column(length = 5000)
	private String body;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getNumeroDDT() {
		return numeroDDT;
	}

	public void setNumeroDDT(String numeroDDT) {
		this.numeroDDT = numeroDDT;
	}
}
