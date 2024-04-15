package com.nttdata.users.api.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class UserDto {

    private Long id;

    private String nome;

    private String cpf;

    private String email;

    private String senha;

    private UserDtoType tipoUsuario;

    public UserDto() {

    }

}
