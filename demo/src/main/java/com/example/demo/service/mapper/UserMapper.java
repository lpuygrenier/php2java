package com.example.demo.service.mapper;

import com.example.demo.models.User;
import com.example.demo.service.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User entity);
    User toEntity(UserDTO dto);
}
