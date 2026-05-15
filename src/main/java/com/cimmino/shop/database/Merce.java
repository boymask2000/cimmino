package com.cimmino.shop.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Merce")
public class Merce {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "merce_id")
	private Long merce_id;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getMerce_id() {
		return merce_id;
	}

	public void setMerce_id(Long merce_id) {
		this.merce_id = merce_id;
	}
}
