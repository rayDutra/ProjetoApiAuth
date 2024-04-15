package com.nttdata.users.api.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@AllArgsConstructor
@Entity
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cpf")
    private String cpf;

    @Column(name= "email")
    private String email;

    @Column(name = "senha")
    private String senha;

    @Column(name = "tipo_usuario")
    @Enumerated(EnumType.STRING)
    private UserEntityType tipoUsuario;

    public UserEntity() {

    }
    public void setSenha(String senha) {
        this.senha = new BCryptPasswordEncoder().encode(senha);
    }
}
