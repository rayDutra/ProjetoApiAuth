package com.nttdata.products.api.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@AllArgsConstructor
@Entity
@Data
public class ProductsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo")
    private int codigo;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "preco")
    private Double preco;

    @Column(name = "quantidade")
    private int quantidade;

    public ProductsEntity() {

    }
}
