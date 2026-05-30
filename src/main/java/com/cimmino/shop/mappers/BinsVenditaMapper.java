package com.cimmino.shop.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cimmino.shop.database.Bin;
import com.cimmino.shop.database.BinsVendite;
import com.cimmino.shop.database.dto.BinsVenditaDTO;
@Mapper(componentModel = "spring")
public interface BinsVenditaMapper {

    default BinsVendite toEntity(BinsVenditaDTO dto) {

        if (dto == null) {
            return null;
        }

        BinsVendite entity = new BinsVendite();

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

    List<BinsVendite> toEntityList(List<BinsVenditaDTO> dtoList);

    @Mapping(source = "bin.id", target = "binId")
    @Mapping(source = "bin.name", target = "binName")
    BinsVenditaDTO toDto(BinsVendite entity);

    @Mapping(source = "bin.id", target = "binId")
    @Mapping(source = "bin.name", target = "binName")
    List<BinsVenditaDTO> toDtoList(List<BinsVendite> entityList);
}