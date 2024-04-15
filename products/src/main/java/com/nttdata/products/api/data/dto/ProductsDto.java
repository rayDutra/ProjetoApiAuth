package com.nttdata.products.api.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class ProductsDto {

    private Long id;
    private int codigo;
    private String descricao;
    private Double preco;
    private int quantidade;

}
