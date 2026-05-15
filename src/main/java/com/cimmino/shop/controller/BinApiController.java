package com.cimmino.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cimmino.shop.database.Bin;
import com.cimmino.shop.database.BinRepository;

@RestController
@RequestMapping("/api/bin")
public class BinApiController {

    @Autowired
    private BinRepository binRepository;

    @GetMapping("/{id}/peso")
    public Integer getPeso(@PathVariable Long id) {

        Integer vv = binRepository.findById(id)
                .map(Bin::getPeso_lordo)
                .orElse((int) 0.0);
        
        return vv;
    }
}