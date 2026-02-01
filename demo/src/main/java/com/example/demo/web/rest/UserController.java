package com.example.demo.web.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.security.UserDetailsImpl;
import com.example.demo.service.UserService;
import com.example.demo.service.dto.UserDTO;

import lombok.AllArgsConstructor;

/**
 * Partie Controller de la gestion MVC.
 * Gère les requêtes HTTP liées aux ingrédients.
 * Les contrôleurs peuvent aussi s'appeler XXXApi, XXXResource.
 *
 * Grâce à la gestion des exceptions globales (GlobalErrorHandler.java),
 * nous n'avons pas besoin de gérer les erreurs dans les contrôleurs.
 */
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
/**
 * On oblige toutes les routes de UserController d'avoir à minima le rôle USER.
 * L'annotation est inutile car dans @SecurityConfig.java on a configuré pour protéger toutes les routes de l'api.
 * Donc notre utilisateur aura forcément le rôle USER, mais on peut protéger avec d'autres rôles.
 * (ex: Role ADMIN ou encore via des droits spécifiques: CAN_REMOVE, CAN_READ, CAN_WRITE)
 */
@PreAuthorize("hasRole('USER')")
public class UserController {
    private final UserService userService;

    /**
     * On oblige toutes cette de UserController d'avoir à minima le rôle USER.
     * (Elle est inutile car il y a déjà l'annotation sur le contrôleur, c'est pour l'exemple)
     */
    @PreAuthorize("hasRole('USER')") 
    @GetMapping
    public List<UserDTO> getAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Long id) {
        UserDTO dto = userService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/account")
    public ResponseEntity<UserDTO> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(userService.findById(userDetails.getId()));
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO dto) {
        UserDTO created = userService.save(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO dto) {
        UserDTO updated = userService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
