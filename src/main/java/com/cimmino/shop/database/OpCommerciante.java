package com.cimmino.shop.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "OpCommerciante")
public class OpCommerciante {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "op_id")
	private Long id;

	@ManyToOne
    @JoinColumn(name = "comm_id") // FK nella tabella bins
	private Commerciante commerciante;
}
