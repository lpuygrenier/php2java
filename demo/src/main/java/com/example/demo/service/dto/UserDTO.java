package com.example.demo.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Par définition, un DTO (Data Transfer Object) est un objet simplement utilisé pour le transfert de données.
 * Il n'y a pas d'interêt à pouvoir modifier un DTO une fois créé, c'est pourquoi on utilise ici un record.
 * 
 * Un record est pensé pour ce cas précis, c'est une structure de données immuable. (qui ne peut pas être modifiée après sa création)
 * Java génère automatiquement constructeur, equals, hashCode, toString.
 * 
 * https://www.jmdoudoux.fr/java/dej/chap-records.htm
 * 
 * On peut utiliser la validation jakarta pour valider facilement les DTO. Il suffit d'annoter le champ que l'on veut valider.
 * Si une requête ne passe pas la validation, Spring Boot renverra directement une erreur, sans même passer dans la fonction du contrôleur.
 */
public record UserDTO(
    Long id,
    
    @NotBlank
    @Size(min = 3, max = 50)
    String username,
    
    @NotBlank
    @Email
    String email
) {}
