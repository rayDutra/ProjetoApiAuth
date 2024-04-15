package com.nttdata.products.api.service;

import com.nttdata.products.api.data.dto.ProductsDto;
import com.nttdata.products.api.data.entity.ProductsEntity;
import com.nttdata.products.api.data.mapper.ProductsMapper;
import com.nttdata.products.api.exception.ProductServiceException;
import com.nttdata.products.api.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ProductsMapper productsMapper;

    @Override
    public ProductsDto saveProduct(ProductsDto produtoDto) {
        try {
            ProductsEntity produtoEntity = productsMapper.toEntity(produtoDto);
            ProductsEntity savedEntity = productsRepository.save(produtoEntity);
            return productsMapper.toDto(savedEntity);
        } catch (Exception e) {
            throw new ProductServiceException("Erro ao salvar o produto: " + e.getMessage());
        }
    }

    @Override
    public void deleteProduct(Long id) {
        try {
            productsRepository.deleteById(id);
        } catch (Exception e) {
            throw new ProductServiceException("Erro ao excluir o produto com ID " + id + ": " + e.getMessage());
        }
    }

    @Override
    public ProductsDto updateProduct(Long id, ProductsDto produtoDto) {
        try {
            ProductsEntity produtoEntity = productsMapper.toEntity(produtoDto);
            produtoEntity.setId(id);
            ProductsEntity updatedEntity = productsRepository.save(produtoEntity);
            return productsMapper.toDto(updatedEntity);
        } catch (Exception e) {
            throw new ProductServiceException("Erro ao atualizar o produto com ID " + id + ": " + e.getMessage());
        }
    }

    @Override
    public Page<ProductsDto> listProducts() {
        try {
            Pageable pageable = PageRequest.of(0, 10);
            Page<ProductsEntity> productsPage = productsRepository.findAll(pageable);
            return productsPage.map(productsMapper::toDto);
        } catch (Exception e) {
            throw new ProductServiceException("Erro ao listar os produtos: " + e.getMessage());
        }
    }

    @Override
    public ProductsDto getProductById(Long id) {
        try {
            return productsRepository.findById(id)
                .map(productsMapper::toDto)
                .orElseThrow(() -> new ProductServiceException("Produto não encontrado com o ID: " + id));
        } catch (Exception e) {
            throw new ProductServiceException("Erro ao buscar o produto com ID " + id + ": " + e.getMessage());
        }
    }

    @Override
    public ProductsDto searchProductByCode(Integer codigo) {
        try {
            ProductsEntity productEntity = productsRepository.findByCodigo(codigo);
            if (productEntity != null) {
                return productsMapper.toDto(productEntity);
            } else {
                throw new ProductServiceException("Produto não encontrado com o código: " + codigo);
            }
        } catch (Exception e) {
            throw new ProductServiceException("Erro ao buscar o produto com código " + codigo + ": " + e.getMessage());
        }
    }
}

