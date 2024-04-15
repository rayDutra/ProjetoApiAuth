package com.nttdata.users.api.service;

import com.nttdata.users.api.data.dto.UserDto;

public interface UserService {

    UserDto registerUser(UserDto user);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    UserDto findByEmail(String email);
}
