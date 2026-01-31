package com.example.demo.service.dto;

/**
 * Par définition, un DTO (Data Transfer Object) est un objet simplement utilisé pour le transfert de données.
 * Il n'y a pas d'interêt à pouvoir modifier un DTO une fois créé, c'est pourquoi on utilise ici un record.
 * 
 * Un record est pensé pour ce cas précis, c'est une structure de données immuable. (qui ne peut pas être modifiée après sa création)
 * Java génère automatiquement constructeur, equals, hashCode, toString.
 * 
 * https://www.jmdoudoux.fr/java/dej/chap-records.htm
 */
public record UnitDTO(Long id, String label) {}
