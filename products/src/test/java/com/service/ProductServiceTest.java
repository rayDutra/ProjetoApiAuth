package com.service;

import com.nttdata.products.api.controller.ProductsController;
import com.nttdata.products.api.data.dto.ProductsDto;
import com.nttdata.products.api.exception.ProductServiceException;
import com.nttdata.products.api.service.ProductsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductsService productsService;

    @InjectMocks
    private ProductsController productsController;

    @Test
    @DisplayName("Teste de criação de produto com sucesso")
    void testCreateProduct_Success() {
        ProductsDto inputDto = new ProductsDto(null, 12345, "Produto de teste", 49.99, 100);
        when(productsService.saveProduct(any(ProductsDto.class))).thenReturn(inputDto);

        ResponseEntity<ProductsDto> response = productsController.createProduct(inputDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(inputDto.getCodigo(), response.getBody().getCodigo());
        assertEquals(inputDto.getDescricao(), response.getBody().getDescricao());
        assertEquals(inputDto.getPreco(), response.getBody().getPreco());
        assertEquals(inputDto.getQuantidade(), response.getBody().getQuantidade());
    }

    @Test
    @DisplayName("Teste de falha na criação de produto")
    void testCreateProduct_Failure() {
        ProductsDto inputDto = new ProductsDto(null, 12345, "Produto de teste", 49.99, 100);
        when(productsService.saveProduct(any(ProductsDto.class))).thenThrow(new RuntimeException("Erro ao salvar produto"));

        ResponseEntity<ProductsDto> response = productsController.createProduct(inputDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Teste de obtenção de produto por ID com sucesso")
    void testGetProductById_Success() {
        Long productId = 1L;
        ProductsDto expectedProduct = new ProductsDto(productId, 12345, "Produto de teste", 49.99, 100);
        when(productsService.getProductById(productId)).thenReturn(expectedProduct);

        ResponseEntity<ProductsDto> response = productsController.getProductById(productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedProduct, response.getBody());
    }

    @Test
    @DisplayName("Teste de obtenção de produto por ID não encontrado")
    void testGetProductById_NotFound() {
        Long productId = 1L;
        when(productsService.getProductById(productId)).thenThrow(new ProductServiceException("Produto não encontrado"));

        ResponseEntity<ProductsDto> response = productsController.getProductById(productId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Teste de atualização de produto com sucesso")
    void testUpdateProduct_Success() {
        Long productId = 1L;
        ProductsDto updatedProductDto = new ProductsDto(productId, 12345, "Produto atualizado", 59.99, 150);
        when(productsService.updateProduct(productId, updatedProductDto)).thenReturn(updatedProductDto);

        ResponseEntity<ProductsDto> response = productsController.updateProduct(productId, updatedProductDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedProductDto, response.getBody());
    }

    @Test
    @DisplayName("Teste de exclusão de produto com sucesso")
    void testDeleteProduct_Success() {
        Long productId = 1L;

        ResponseEntity<Void> response = productsController.deleteProduct(productId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }
}
