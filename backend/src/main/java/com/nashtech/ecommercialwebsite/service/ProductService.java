package com.nashtech.ecommercialwebsite.service;

import com.nashtech.ecommercialwebsite.data.entity.Product;

import java.util.Optional;


public interface ProductService {

    Optional<Product> findByID(String id);
}
