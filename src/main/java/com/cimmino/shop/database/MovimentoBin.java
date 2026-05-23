package com.cimmino.shop.database;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MovimentiBin")
public class MovimentoBin {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	private LocalDate data;

	private String binName;
	
	private int numBins;

	private int inout = 0; // 0 == OUT -- 1 == IN

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public int getInout() {
		return inout;
	}

	public void setInout(int inout) {
		this.inout = inout;
	}



	public int getNumBins() {
		return numBins;
	}

	public void setNumBins(int numBins) {
		this.numBins = numBins;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getBinName() {
		return binName;
	}

	public void setBinName(String binName) {
		this.binName = binName;
	}



}
