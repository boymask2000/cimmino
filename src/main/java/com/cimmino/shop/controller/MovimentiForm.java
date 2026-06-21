package com.cimmino.shop.controller;

import java.util.List;

import com.cimmino.shop.database.BinsArrivi;

public class MovimentiForm {


    private List<BinMovimento> binsarrivi;

    public List<BinMovimento> getBinsarrivi() {
        return binsarrivi;
    }

    public void setBinsarrivi(List<BinMovimento> binsarrivi) {
        this.binsarrivi = binsarrivi;
    }
}