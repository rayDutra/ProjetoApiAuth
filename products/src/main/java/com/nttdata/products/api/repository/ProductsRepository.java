package com.nttdata.products.api.repository;

import com.nttdata.products.api.data.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<ProductsEntity, Long> {
    ProductsEntity findByCodigo(Integer codigo);
}
