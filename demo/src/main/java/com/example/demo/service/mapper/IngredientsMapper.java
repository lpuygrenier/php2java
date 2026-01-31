package com.example.demo.service.mapper;

import com.example.demo.models.Ingredients;
import com.example.demo.service.dto.IngredientsDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UnitMapper.class)
public interface IngredientsMapper {
    IngredientsDTO toDto(Ingredients entity);
    Ingredients toEntity(IngredientsDTO dto);
}
