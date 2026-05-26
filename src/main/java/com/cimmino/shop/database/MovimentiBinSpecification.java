package com.cimmino.shop.database;


import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class MovimentiBinSpecification {

    public static Specification<MovimentoBin> dataDa(LocalDate dataDa) {
        return (root, query, cb) ->
                dataDa == null ? null :
                cb.greaterThanOrEqualTo(root.get("data"), dataDa);
    }

    public static Specification<MovimentoBin> dataA(LocalDate dataA) {
        return (root, query, cb) ->
                dataA == null ? null :
                cb.lessThanOrEqualTo(root.get("data"), dataA);
    }

    public static Specification<MovimentoBin> bin(String bin) {
        return (root, query, cb) ->
                (bin == null || bin.isBlank()) ? null :
                cb.equal(root.get("binName"), bin);
    }
}