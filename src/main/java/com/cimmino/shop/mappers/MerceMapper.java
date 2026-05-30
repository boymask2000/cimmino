package com.cimmino.shop.mappers;


import org.mapstruct.Mapper;

import com.cimmino.shop.database.Merce;
import com.cimmino.shop.database.dto.MerceDTO;

@Mapper(componentModel = "spring")
public interface MerceMapper {

    MerceDTO toDto(Merce entity);

    Merce toEntity(MerceDTO dto);
}