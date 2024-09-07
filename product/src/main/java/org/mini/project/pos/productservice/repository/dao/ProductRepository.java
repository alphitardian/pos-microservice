package org.mini.project.pos.productservice.repository.dao;

import org.mini.project.pos.productservice.repository.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findById(long id);
}