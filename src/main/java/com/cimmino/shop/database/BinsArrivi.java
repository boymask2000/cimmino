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
@Table(name = "BinsArrivi")
public class BinsArrivi {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "binsarrivi_id")
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "bin_id") // FK nella tabella bins
    private Bin bin;
	
	private int numBins;

	 @ManyToOne
	    @JoinColumn(name = "arrivo_id")
	    private Arrivi arrivoEntity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Bin getBin() {
		return bin;
	}

	public void setBin(Bin bin) {
		this.bin = bin;
	}

	public int getNumBins() {
		return numBins;
	}

	public void setNumBins(int numBins) {
		this.numBins = numBins;
	}

	public Arrivi getArrivoEntity() {
		return arrivoEntity;
	}

	public void setArrivoEntity(Arrivi arrivoEntity) {
		this.arrivoEntity = arrivoEntity;
	}
}
