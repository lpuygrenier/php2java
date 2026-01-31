package com.example.demo.service.mapper;

import com.example.demo.models.Unit;
import com.example.demo.service.dto.UnitDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UnitMapper {
    UnitDTO toDto(Unit entity);
    Unit toEntity(UnitDTO dto);
}
