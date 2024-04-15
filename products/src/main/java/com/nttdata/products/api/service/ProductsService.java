package com.nttdata.products.api.service;

import com.nttdata.products.api.data.dto.ProductsDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductsService {

    ProductsDto saveProduct(ProductsDto produtoDto);

    void deleteProduct(Long id);

    ProductsDto updateProduct(Long id, ProductsDto produtoDto);

    Page<ProductsDto> listProducts();

    ProductsDto getProductById(Long id);

    ProductsDto searchProductByCode(Integer codigo);

}
