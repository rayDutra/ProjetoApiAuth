package com.nttdata.authentication.api.client;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class User {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private UserDtoType tipoUsuario;
}
