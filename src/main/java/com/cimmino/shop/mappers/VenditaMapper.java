package com.cimmino.shop.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cimmino.shop.database.Vendite;
import com.cimmino.shop.database.dto.VenditaDTO;



@Mapper(componentModel = "spring")
public interface VenditaMapper {

    @Mapping(source = "commercianteId", target = "commerciante.commerciante_id")
    Vendite toEntity(VenditaDTO dto);
}