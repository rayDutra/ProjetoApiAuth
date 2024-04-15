package com.nttdata.products.api.data.mapper;

import com.nttdata.products.api.data.dto.ProductsDto;
import com.nttdata.products.api.data.entity.ProductsEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductsMapper {

    public ProductsDto toDto(ProductsEntity productsEntity) {
        return new ProductsDto(
            productsEntity.getId(),
            productsEntity.getCodigo(),
            productsEntity.getDescricao(),
            productsEntity.getPreco(),
            productsEntity.getQuantidade()
        );
    }

    public ProductsEntity toEntity(ProductsDto productDto) {
        return new ProductsEntity(
            productDto.getId(),
            productDto.getCodigo(),
            productDto.getDescricao(),
            productDto.getPreco(),
            productDto.getQuantidade()
        );
    }
}
