package com.cimmino.shop.database.dto;

import java.math.BigDecimal;
import java.util.List;

public class GruppoVenditeDTO {

    private Long id;

    private List<Long> venditaIds;
    
    private BigDecimal pesoLordoTotale; 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getVenditaIds() {
        return venditaIds;
    }

    public void setVenditaIds(List<Long> venditaIds) {
        this.venditaIds = venditaIds;
    }

	public BigDecimal getPesoLordoTotale() {
		return pesoLordoTotale;
	}

	public void setPesoLordoTotale(BigDecimal pesoLordoTotale) {
		this.pesoLordoTotale = pesoLordoTotale;
	}
}