package com.cimmino.shop.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cimmino.shop.database.Arrivi;
import com.cimmino.shop.database.BinsArrivi;
import com.cimmino.shop.database.dto.ArriviDTO;
import com.cimmino.shop.database.dto.BinsArriviDTO;



@Mapper(componentModel = "spring")
public interface ArriviMapper {

    // =========================
    // ENTITY -> DTO
    // =========================

   // @Mapping(source = "merce.merce_id", target = "merceId")
   // @Mapping(source = "merce.name", target = "merceName")
    ArriviDTO toDto(Arrivi entity);

    // =========================
    // DTO -> ENTITY
    // =========================

   // @Mapping(source = "merceId", target = "merce.merce_id")
    Arrivi toEntity(ArriviDTO dto);

    // =========================
    // BINS
    // =========================

    List<BinsArriviDTO> mapBins(List<BinsArrivi> bins);
    List<BinsArrivi> mapBinsDto(List<BinsArriviDTO> bins);

}