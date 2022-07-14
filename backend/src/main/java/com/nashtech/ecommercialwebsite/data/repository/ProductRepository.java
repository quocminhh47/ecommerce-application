package com.nashtech.ecommercialwebsite.data.repository;

import com.nashtech.ecommercialwebsite.data.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findProductByBrand_Name(String brandName, Pageable pageable);

    // find product which is available (hidden = false)
    Page<Product> findProductByHidden(boolean hidden, Pageable pageable);

    Page<Product> findProductByGender (boolean gender, Pageable pageable);

    @Query(value = "select  id from products where hidden = ?1 ", nativeQuery = true)
    List<Integer> findAllProductIds(boolean hidden);

}
