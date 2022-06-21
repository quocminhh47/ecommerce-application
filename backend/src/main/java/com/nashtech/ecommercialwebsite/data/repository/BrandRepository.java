package com.nashtech.ecommercialwebsite.data.repository;


import com.nashtech.ecommercialwebsite.data.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand,Integer> {
}
