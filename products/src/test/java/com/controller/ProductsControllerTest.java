package com.controller;
import com.nttdata.products.api.controller.ProductsController;
import com.nttdata.products.api.data.dto.ProductsDto;
import com.nttdata.products.api.exception.ProductServiceException;
import com.nttdata.products.api.service.ProductsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductsControllerTest {

    @Mock
    private ProductsService productsService;

    @InjectMocks
    private ProductsController productsController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Teste criar um novo produto")
    public void testCreateProduct() {
        ProductsDto inputDto = new ProductsDto(null, 01, "Produto A", 100.0, 5);
        ProductsDto outputDto = new ProductsDto(1L, 01, "Produto A", 100.0, 5);
        when(productsService.saveProduct(inputDto)).thenReturn(outputDto);
        ResponseEntity<ProductsDto> responseEntity = productsController.createProduct(inputDto);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(outputDto, responseEntity.getBody());
        verify(productsService, times(1)).saveProduct(inputDto);
    }

    @Test
    @DisplayName("Teste listar todos os produtos")
    public void testGetAllProducts() {
        ProductsDto dto1 = new ProductsDto(1L, 01, "Produto A", 100.0, 5);
        ProductsDto dto2 = new ProductsDto(2L, 02, "Produto B", 150.0, 3);
        List<ProductsDto> productsList = List.of(dto1, dto2);
        Page<ProductsDto> pageProducts = new PageImpl<>(productsList);
        when(productsService.listProducts()).thenReturn(pageProducts);
        ResponseEntity<List<ProductsDto>> responseEntity = productsController.getAllProducts();
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(productsList, responseEntity.getBody());
        verify(productsService, times(1)).listProducts();
    }
    @Test
    @DisplayName("Teste buscar produto por ID inexistente")
    public void testGetProductById_NonExistingProduct() {
        Long productId = 1L;
        when(productsService.getProductById(productId)).thenThrow(new ProductServiceException("Produto não encontrado"));
        ResponseEntity<ProductsDto> responseEntity = productsController.getProductById(productId);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(productsService, times(1)).getProductById(productId);
    }
}
