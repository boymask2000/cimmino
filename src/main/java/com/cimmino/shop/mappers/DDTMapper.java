package com.cimmino.shop.mappers;


import java.util.List;

import org.mapstruct.Mapper;

import com.cimmino.shop.database.DDT;
import com.cimmino.shop.database.dto.DDTDTO;

@Mapper(componentModel = "spring")
public interface DDTMapper {

    DDTDTO toDto(DDT ddt);

    DDT toEntity(DDTDTO dto);

    List<DDTDTO> toDtoList(List<DDT> ddtList);

    List<DDT> toEntityList(List<DDTDTO> dtoList);
}