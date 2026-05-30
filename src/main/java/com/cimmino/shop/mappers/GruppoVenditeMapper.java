package com.cimmino.shop.mappers;



import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cimmino.shop.database.GruppoVendite;
import com.cimmino.shop.database.Vendita;
import com.cimmino.shop.database.dto.GruppoVenditeDTO;

@Mapper(componentModel = "spring")
public interface GruppoVenditeMapper {

    @Mapping(
        target = "venditaIds",
        expression = "java(mapVenditaIds(entity.getVendite()))"
    )
    GruppoVenditeDTO toDto(GruppoVendite entity);

    List<GruppoVenditeDTO> toDtoList(List<GruppoVendite> entities);

    default List<Long> mapVenditaIds(List<Vendita> vendite) {

        if (vendite == null) {
            return null;
        }

        return vendite.stream()
                .map(Vendita::getId)
                .collect(Collectors.toList());
    }
}