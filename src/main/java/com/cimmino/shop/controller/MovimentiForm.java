package com.cimmino.shop.controller;

import java.util.List;

import com.cimmino.shop.database.BinsArrivi;

public class MovimentiForm {


    private List<BinsArrivi> binsarrivi;

    public List<BinsArrivi> getBinsarrivi() {
        return binsarrivi;
    }

    public void setBinsarrivi(List<BinsArrivi> binsarrivi) {
        this.binsarrivi = binsarrivi;
    }
}