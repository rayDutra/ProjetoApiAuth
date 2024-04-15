package com.nttdata.users.api.repository;

import com.nttdata.users.api.data.dto.UserDto;
import com.nttdata.users.api.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findByCpf(String cpf);

    Optional<UserEntity> findByEmail(String email);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);
}
