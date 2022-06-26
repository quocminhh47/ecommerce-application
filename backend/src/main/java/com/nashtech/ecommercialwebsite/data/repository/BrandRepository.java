package com.nashtech.ecommercialwebsite.data.repository;


import com.nashtech.ecommercialwebsite.data.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand,Integer> {

    Optional<Brand> findBrandByName(String name);
}
