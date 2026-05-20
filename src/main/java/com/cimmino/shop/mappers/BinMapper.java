package com.cimmino.shop.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cimmino.shop.database.Bin;
import com.cimmino.shop.database.dto.BinDTO;

@Mapper(componentModel = "spring")
public interface BinMapper {

    BinDTO toDto(Bin bin);

    List<BinDTO> toDtoList(List<Bin> bins);
}