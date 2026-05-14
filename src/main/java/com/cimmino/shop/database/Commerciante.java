package com.cimmino.shop.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Commerciante")
public class Commerciante {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "commerciante_id")
	private Long commerciante_id;
	
	private String name;

	public Long getCommerciante_id() {
		return commerciante_id;
	}

	public void setCommerciante_id(Long commerciante_id) {
		this.commerciante_id = commerciante_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
