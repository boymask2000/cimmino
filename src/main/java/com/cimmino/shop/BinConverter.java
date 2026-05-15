package com.cimmino.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.cimmino.shop.database.Bin;
import com.cimmino.shop.database.BinRepository;

@Component
public class BinConverter implements Converter<String, Bin> {

    @Autowired
    private BinRepository binRepository;

    @Override
    public Bin convert(String id) {
        if (id == null || id.isEmpty()) return null;
        return binRepository.findById(Long.valueOf(id)).orElse(null);
    }
}