package com.nttdata.users.api.service;

import com.nttdata.users.api.data.dto.UserDto;
import com.nttdata.users.api.data.entity.UserEntity;
import com.nttdata.users.api.data.mapper.UserMapper;
import com.nttdata.users.api.exception.UserRegistrationException;
import com.nttdata.users.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDto registerUser(UserDto user) {
        try {
            if (!isValidCPF(user.getCpf())) {
                throw new IllegalArgumentException("CPF inválido");
            }

            // Verificar se o CPF já está cadastrado
            if (existsByCpf(user.getCpf())) {
                throw new UserRegistrationException("CPF já cadastrado");
            }

            // Codificar a senha antes de salvar o usuário
            user.setSenha(passwordEncoder.encode(user.getSenha()));
            UserEntity userEntity = userRepository.save(userMapper.toEntity(user));

            return userMapper.toDto(userEntity);
        } catch (Exception e) {
            throw new UserRegistrationException("Erro ao registrar usuário: " + e.getMessage());
        }
    }

    @Override
    public boolean existsByCpf(String cpf) {
        try {
            // Verificar se existe um usuário com o CPF especificado
            return userRepository.existsByCpf(cpf);
        } catch (Exception e) {
            throw new UserRegistrationException("Erro ao verificar CPF: " + e.getMessage());
        }
    }

    @Override
    public UserDto findByEmail(String email) {
        try {
            Optional<UserEntity> userEntity = userRepository.findByEmail(email);
            if (userEntity.isPresent()) {
                return userMapper.toDto(userEntity.get());
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new UserRegistrationException("Erro ao buscar usuário por e-mail: " + e.getMessage());
        }
    }
    @Override
    public boolean existsByEmail(String email) {
        try {
            Optional<UserEntity> userEntity = userRepository.findByEmail(email);
            return userEntity.isPresent();
        } catch (Exception e) {
            throw new UserRegistrationException("Erro ao registrar e-mail: " + e.getMessage());
        }
    }
    private boolean isValidCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");
        if (cpf.length() != 11) {
            return false;
        }
        int[] digits = new int[11];
        for (int i = 0; i < 11; i++) {
            digits[i] = Integer.parseInt(cpf.substring(i, i + 1));
        }
        if (allDigitsAreEqual(digits)) {
            return false;
        }
        int sum = 0;
        int weight = 10;
        for (int i = 0; i < 9; i++) {
            sum += digits[i] * weight;
            weight--;
        }
        int remainder = sum % 11;
        int digit1 = (remainder < 2) ? 0 : (11 - remainder);
        if (digits[9] != digit1) {
            return false;
        }
        sum = 0;
        weight = 11;
        for (int i = 0; i < 10; i++) {
            sum += digits[i] * weight;
            weight--;
        }
        remainder = sum % 11;
        int digit2 = (remainder < 2) ? 0 : (11 - remainder);
        if (digits[10] != digit2) {
            return false;
        }
        return true;
    }
    private boolean allDigitsAreEqual(int[] digits) {
        for (int i = 1; i < digits.length; i++) {
            if (digits[i] != digits[0]) {
                return false;
            }
        }
        return true;
    }
}
