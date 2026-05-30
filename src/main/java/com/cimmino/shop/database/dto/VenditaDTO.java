package com.cimmino.shop.database.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class VenditaDTO {

    private Long id;

    private Long arrivoId;

    private Long commercianteId;
    private String commercianteNome;

    private LocalDate data;

    private String ddt;

    private BigDecimal peso_lordo;

    private BigDecimal nettoDiTara;
    private BigDecimal nettoDiScarto;
    private BigDecimal tara;

    private BigDecimal media;
    private BigDecimal scarto;

    private BigDecimal prezzo;
    private BigDecimal importo;

    private BigDecimal frigo;

    private String key;

    // versione light (NO ENTITY LOOP)
    private List<BinsVenditaDTO> bins;

    // campo derivato (transient equivalente DTO)
    private Integer numeroTotaleBins;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getArrivoId() {
		return arrivoId;
	}

	public void setArrivoId(Long arrivoId) {
		this.arrivoId = arrivoId;
	}

	public Long getCommercianteId() {
		return commercianteId;
	}

	public void setCommercianteId(Long commercianteId) {
		this.commercianteId = commercianteId;
	}

	public String getCommercianteNome() {
		return commercianteNome;
	}

	public void setCommercianteNome(String commercianteNome) {
		this.commercianteNome = commercianteNome;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getDdt() {
		return ddt;
	}

	public void setDdt(String ddt) {
		this.ddt = ddt;
	}

	public BigDecimal getPeso_lordo() {
		return peso_lordo;
	}

	public void setPeso_lordo(BigDecimal peso_lordo) {
		this.peso_lordo = peso_lordo;
	}

	public BigDecimal getNettoDiTara() {
		return nettoDiTara;
	}

	public void setNettoDiTara(BigDecimal nettoDiTara) {
		this.nettoDiTara = nettoDiTara;
	}

	public BigDecimal getNettoDiScarto() {
		return nettoDiScarto;
	}

	public void setNettoDiScarto(BigDecimal nettoDiScarto) {
		this.nettoDiScarto = nettoDiScarto;
	}

	public BigDecimal getTara() {
		return tara;
	}

	public void setTara(BigDecimal tara) {
		this.tara = tara;
	}

	public BigDecimal getMedia() {
		return media;
	}

	public void setMedia(BigDecimal media) {
		this.media = media;
	}

	public BigDecimal getScarto() {
		return scarto;
	}

	public void setScarto(BigDecimal scarto) {
		this.scarto = scarto;
	}

	public BigDecimal getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(BigDecimal prezzo) {
		this.prezzo = prezzo;
	}

	public BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public BigDecimal getFrigo() {
		return frigo;
	}

	public void setFrigo(BigDecimal frigo) {
		this.frigo = frigo;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<BinsVenditaDTO> getBins() {
		return bins;
	}

	public void setBins(List<BinsVenditaDTO> bins) {
		this.bins = bins;
	}

	public Integer getNumeroTotaleBins() {
		return numeroTotaleBins;
	}

	public void setNumeroTotaleBins(Integer numeroTotaleBins) {
		this.numeroTotaleBins = numeroTotaleBins;
	}

    // getters & setters
}