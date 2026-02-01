package com.example.demo.repository;

import com.example.demo.models.Ingredients;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository, on extends l'interface JpaRepository, ce qui fournit automatiquement
 * les opérations CRUD de base sur l'entité User : lecture, écriture, mise à jour
 * et suppression (par exemple findById, findAll, save, deleteById)
 * Il est également possible de déclarer des méthodes de requête dérivées (Voir @UserRepository.java) de leur nom
 * (par exemple findByUsername, findByEmailAndActiveTrue) ou d'utiliser
 * l'annotation @Query pour définir des requêtes JPQL ou SQL natives si besoin.
 */
@Repository
public interface IngredientsRepository extends JpaRepository<Ingredients, Long> {
    /** Exemple de requête JPQL */
    // La jointure entre Ingredients et Unit sera automatiquement faite par JPA
    @Query("""
        SELECT i
        FROM Ingredients i
        WHERE i.unit.label = :unitLabel
    """)
    List<Ingredients> findIngredientsWithUnitLabel(@Param("unitLabel") String unitLabel);

    /** Exemple de requête native */
    @Query(value = "select * from ingredients i where i.name like concat(:letter, '%')", nativeQuery = true)
    List<Ingredients> findIngredientWithFirstLetter(@Param("letter") String letter);
}
