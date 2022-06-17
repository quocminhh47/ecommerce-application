package com.nashtech.ecommercialwebsite.repository;

import com.nashtech.ecommercialwebsite.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {

}
