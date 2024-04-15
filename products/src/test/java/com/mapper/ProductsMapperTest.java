package com.mapper;

import com.nttdata.products.api.data.dto.ProductsDto;
import com.nttdata.products.api.data.entity.ProductsEntity;
import com.nttdata.products.api.data.mapper.ProductsMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class ProductsMapperTest {

    private ProductsMapper productsMapper;

    @BeforeEach
    public void setUp() {
        productsMapper = new ProductsMapper();
    }

    @Test
    @DisplayName("Teste de mapeamento de ProductsEntity para ProductsDto")
    public void testToDto() {
        ProductsEntity entity = new ProductsEntity(1L, 01, "Produto A", 100.0, 5);
        ProductsDto dto = productsMapper.toDto(entity);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getCodigo(), dto.getCodigo());
        assertEquals(entity.getDescricao(), dto.getDescricao());
        assertEquals(entity.getPreco(), dto.getPreco());
        assertEquals(entity.getQuantidade(), dto.getQuantidade());
    }

    @Test
    @DisplayName("Teste de mapeamento de ProductsDto para ProductsEntity")
    public void testToEntity() {
        ProductsDto dto = new ProductsDto(1L, 01, "Produto A", 100.0, 5);
        ProductsEntity entity = productsMapper.toEntity(dto);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getCodigo(), entity.getCodigo());
        assertEquals(dto.getDescricao(), entity.getDescricao());
        assertEquals(dto.getPreco(), entity.getPreco());
        assertEquals(dto.getQuantidade(), entity.getQuantidade());
    }
}
