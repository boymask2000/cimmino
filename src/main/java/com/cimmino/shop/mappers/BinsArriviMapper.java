package com.cimmino.shop.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cimmino.shop.database.Bin;
import com.cimmino.shop.database.BinsArrivi;
import com.cimmino.shop.database.dto.BinsArriviDTO;
@Mapper(componentModel = "spring")
public interface BinsArriviMapper {

    default BinsArrivi toEntity(BinsArriviDTO dto) {

        if (dto == null) {
            return null;
        }

        BinsArrivi entity = new BinsArrivi();

        entity.setPesoLordo(dto.getPesoLordo());
        entity.setPesoNetto(dto.getPesoNetto());
        entity.setNumBins(dto.getNumBins());
      //  entity.setNumBinSold(dto.getNumBinSold());

        if (dto.getBinId() != null) {

            Bin bin = new Bin();

            bin.setId(dto.getBinId());
            bin.setName(dto.getBinName());

            entity.setBin(bin);
        }

        return entity;
    }

    List<BinsArrivi> toEntityList(List<BinsArriviDTO> dtoList);

    @Mapping(source = "bin.id", target = "binId")
    @Mapping(source = "bin.name", target = "binName")
    BinsArriviDTO toDto(BinsArrivi entity);

    List<BinsArriviDTO> toDtoList(List<BinsArrivi> entityList);
}