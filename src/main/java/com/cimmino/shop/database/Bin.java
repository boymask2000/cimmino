package com.cimmino.shop.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Bins")
public class Bin {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bin_id")
	private Long id;
	
	private String name;
	
	private int peso_lordo;
	private int tara;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPeso_lordo() {
		return peso_lordo;
	}
	public void setPeso_lordo(int peso_lordo) {
		this.peso_lordo = peso_lordo;
	}
	public int getTara() {
		return tara;
	}
	public void setTara(int tara) {
		this.tara = tara;
	}

}
