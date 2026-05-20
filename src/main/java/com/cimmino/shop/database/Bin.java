package com.cimmino.shop.database;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "bins")
public class Bin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bin_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    // usa naming Java standard (NON snake_case nei field Java)
    @Column(name = "peso_lordo")
    private Integer pesoLordo;

    @Column(name = "tara")
    private int tara;

    // =========================
    // RELAZIONI (IMPORTANTISSIMO PER DEBUG E QUERY)
    // =========================

    @OneToMany(mappedBy = "bin")
    private List<BinsArrivi> arrivi;

//    @OneToMany(mappedBy = "bin")
//    private List<Vendite> vendite;

    // =========================
    // GETTER / SETTER
    // =========================

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

    

    public int getTara() {
        return tara;
    }

    public void setTara(int tara) {
        this.tara = tara;
    }

    public List<BinsArrivi> getArrivi() {
        return arrivi;
    }

    public void setArrivi(List<BinsArrivi> arrivi) {
        this.arrivi = arrivi;
    }

   

	public Integer getPesoLordo() {
		return pesoLordo;
	}

	public void setPesoLordo(Integer pesoLordo) {
		this.pesoLordo = pesoLordo;
	}
}