package com.example.demo.service;

import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.dto.UserDTO;
import com.example.demo.service.exception.NotFoundAlertException;
import com.example.demo.service.mapper.UserMapper;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private static final String ENTITY_NAME = "User";

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDTO> findAll() {
        return userRepository
                .findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserDTO findById(Long id) {
        return userRepository
                .findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new NotFoundAlertException(ENTITY_NAME, "findbyid", "User not found with id %s".formatted(id)));

    }

    public UserDTO save(UserDTO dto) {
        User entity = userMapper.toEntity(dto);
        return userMapper.toDto(userRepository.save(entity));
    }

    public UserDTO update(Long id, UserDTO dto) {
        return userRepository.findById(id)
                .map(ignored -> {
                    User updated = userMapper.toEntity(dto);
                    updated.setId(id);
                    return userMapper.toDto(userRepository.save(updated));
                })
                .orElseThrow(() -> new NotFoundAlertException(ENTITY_NAME, "update", "User not found with id %s".formatted(id)));
    }

    public boolean delete(Long id) {
        return userRepository.findById(id)
                .map(entity -> {
                    userRepository.delete(entity);
                    return true;
                })
                .orElseThrow(() -> new NotFoundAlertException(ENTITY_NAME, "delete", "User not found with id %s".formatted(id)));
    }
}
