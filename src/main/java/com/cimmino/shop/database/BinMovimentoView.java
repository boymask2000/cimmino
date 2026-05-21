package com.cimmino.shop.database;

public interface BinMovimentoView {
    Long getId();
    String getName();
    Long getTotaleIn();
    Long getTotaleOut();
    Long getSaldo();
}