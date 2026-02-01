package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.User;

/**
 * Repository, on extends l'interface JpaRepository, ce qui fournit automatiquement
 * les opérations CRUD de base sur l'entité User : lecture, écriture, mise à jour
 * et suppression (par exemple findById, findAll, save, deleteById)
 * Il est également possible de déclarer des méthodes de requête dérivées de leur nom
 * (par exemple findByUsername, findByEmailAndActiveTrue) ou d'utiliser
 * l'annotation @Query pour définir des requêtes JPQL ou SQL natives si besoin. (Voir @IngredientsRepository.java)
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /** Requêtes dérivées */
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
