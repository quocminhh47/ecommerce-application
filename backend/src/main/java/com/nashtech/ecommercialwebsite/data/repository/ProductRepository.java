package com.nashtech.ecommercialwebsite.data.repository;

import com.nashtech.ecommercialwebsite.data.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findProductByBrand_Name(String brandName, Pageable pageable);

    // find product which is available (hidden = false)
    Page<Product> findProductByHidden(boolean hidden, Pageable pageable);

}
