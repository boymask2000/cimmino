package com.cimmino.shop.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cimmino.shop.database.Arrivi;
import com.cimmino.shop.database.dto.ArriviDTO;

@Mapper(
    componentModel = "spring",
    uses = {BinsArriviMapper.class, VenditaMapper.class}
)
public interface ArriviMapper {

    // ENTITY → DTO
    @Mapping(source = "merce.merce_id", target = "merceId")
    @Mapping(source = "merce.name", target = "merceNome")
    ArriviDTO toDto(Arrivi entity);

    // DTO → ENTITY
    @Mapping(source = "merceId", target = "merce.merce_id")
    Arrivi toEntity(ArriviDTO dto);

    List<ArriviDTO> toDtoList(List<Arrivi> list);

    List<Arrivi> toEntityList(List<ArriviDTO> list);
}