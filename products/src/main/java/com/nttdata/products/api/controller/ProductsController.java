package com.nttdata.products.api.controller;
import com.nttdata.products.api.data.dto.ProductsDto;
import com.nttdata.products.api.exception.ProductServiceException;
import com.nttdata.products.api.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @PostMapping
    public ResponseEntity<ProductsDto> createProduct(@RequestBody ProductsDto produtoDto) {
        try {
            ProductsDto savedProduct = productsService.saveProduct(produtoDto);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProductsDto>> getAllProducts() {
        try {
            List<ProductsDto> productsList = productsService.listProducts().getContent();
            return new ResponseEntity<>(productsList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductsDto> getProductById(@PathVariable Long id) {
        try {
            ProductsDto productDto = productsService.getProductById(id);
            return new ResponseEntity<>(productDto, HttpStatus.OK);
        } catch (ProductServiceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductsDto> updateProduct(@PathVariable Long id, @RequestBody ProductsDto produtoDto) {
        try {
            ProductsDto updatedProduct = productsService.updateProduct(id, produtoDto);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (ProductServiceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            productsService.deleteProduct(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ProductServiceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search/{codigo}")
    public ResponseEntity<ProductsDto> searchProductByCode(@PathVariable int codigo) {
        try {
            ProductsDto productDto = productsService.searchProductByCode(codigo);
            return new ResponseEntity<>(productDto, HttpStatus.OK);
        } catch (ProductServiceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
