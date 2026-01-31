package com.example.demo.service;

import com.example.demo.models.Ingredients;
import com.example.demo.repository.IngredientsRepository;
import com.example.demo.service.dto.IngredientsDTO;
import com.example.demo.service.exception.NotFoundAlertException;
import com.example.demo.service.mapper.IngredientsMapper;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IngredientsService {
    private static final String ENTITY_NAME = "Ingredients";

    private final IngredientsRepository ingredientsRepository;
    private final IngredientsMapper ingredientsMapper;

    public List<IngredientsDTO> findAll() {
        return ingredientsRepository
                .findAll()
                .stream()
                .map(ingredientsMapper::toDto)
                .toList();
    }

    public IngredientsDTO findById(Long id) {
        return ingredientsRepository
                .findById(id)
                .map(ingredientsMapper::toDto)
                .orElseThrow(() -> new NotFoundAlertException(ENTITY_NAME, "findbyid", "Ingredient not found with id %s".formatted(id)));
    }

    public IngredientsDTO save(IngredientsDTO dto) {
        Ingredients entity = ingredientsMapper.toEntity(dto);
        return ingredientsMapper.toDto(ingredientsRepository.save(entity));
    }

    public IngredientsDTO update(Long id, IngredientsDTO dto) {
        return ingredientsRepository
                .findById(id)
                .map(ignored -> {
                    Ingredients updated = ingredientsMapper.toEntity(dto);
                    updated.setId(id);
                    return ingredientsMapper.toDto(ingredientsRepository.save(updated));
                })
                .orElseThrow(() -> new NotFoundAlertException(ENTITY_NAME, "update", "Ingredient not found with id %s".formatted(id)));
    }

    public boolean delete(Long id) {
        return ingredientsRepository
                .findById(id)
                .map(entity -> {
                    ingredientsRepository.delete(entity);
                    return true;
                })
                .orElseThrow(() -> new NotFoundAlertException(ENTITY_NAME, "delete", "Ingredient not found with id %s".formatted(id)));

    }
}
