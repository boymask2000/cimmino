
package com.cimmino.shop.database.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RiepilogoVendite {

    private Long commercianteId;
    private String commercianteNome;
    private BigDecimal totaleImporto;
    private BigDecimal totalePesoLordo;
    private LocalDate data;
    private Long merceId;
    private String merceNome;

    public RiepilogoVendite(
            Long commercianteId,
            String commercianteNome,
            BigDecimal totaleImporto,
            BigDecimal totalePesoLordo,
            LocalDate data,
            Long merceId,
            String merceNome) {

        this.commercianteId = commercianteId;
        this.commercianteNome = commercianteNome;
        this.totaleImporto = totaleImporto;
        this.totalePesoLordo = totalePesoLordo;
        this.data = data;
        this.merceId = merceId;
        this.merceNome = merceNome;
    }

    public Long getCommercianteId() {
        return commercianteId;
    }

    public String getCommercianteNome() {
        return commercianteNome;
    }

    public BigDecimal getTotaleImporto() {
        return totaleImporto;
    }

    public BigDecimal getTotalePesoLordo() {
        return totalePesoLordo;
    }

    public LocalDate getData() {
        return data;
    }

    public Long getMerceId() {
        return merceId;
    }

    public String getMerceNome() {
        return merceNome;
    }
}
