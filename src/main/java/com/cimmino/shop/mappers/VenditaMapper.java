package com.cimmino.shop.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cimmino.shop.database.Vendita;
import com.cimmino.shop.database.dto.VenditaDTO;



@Mapper(componentModel = "spring")
public interface VenditaMapper {

    @Mapping(source = "commercianteId", target = "commerciante.commerciante_id")
    Vendita toEntity(VenditaDTO dto);
    
    @Mapping(source = "commerciante.commerciante_id", target = "commercianteId")
    VenditaDTO toDto(Vendita entity);
}