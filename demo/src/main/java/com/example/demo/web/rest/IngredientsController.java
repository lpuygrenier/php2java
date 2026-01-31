package com.example.demo.web.rest;

import com.example.demo.service.IngredientsService;
import com.example.demo.service.dto.IngredientsDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Partie Controller de la gestion MVC.
 * Gère les requêtes HTTP liées aux ingrédients.
 * Les contrôleurs peuvent aussi s'appeler XXXApi, XXXResource.
 *
 * Grâce à la gestion des exceptions globales (GlobalErrorHandler.java),
 * nous n'avons pas besoin de gérer les erreurs dans les contrôleurs.
 */
@RestController
@RequestMapping("/api/ingredients")
@AllArgsConstructor
public class IngredientsController {
    private final IngredientsService ingredientsService;

    @GetMapping
    public List<IngredientsDTO> getAll() {
        return ingredientsService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientsDTO> getById(@PathVariable Long id) {
        IngredientsDTO dto = ingredientsService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<IngredientsDTO> create(@RequestBody IngredientsDTO dto) {
        IngredientsDTO created = ingredientsService.save(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngredientsDTO> update(@PathVariable Long id, @RequestBody IngredientsDTO dto) {
        IngredientsDTO updated = ingredientsService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ingredientsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
