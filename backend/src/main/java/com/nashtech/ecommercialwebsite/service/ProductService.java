package com.nashtech.ecommercialwebsite.service;

import com.nashtech.ecommercialwebsite.data.entity.Product;
import com.nashtech.ecommercialwebsite.dto.response.ProductResponse;

import java.util.Optional;


public interface ProductService {

    Optional<Product> findByID(String id);

    ProductResponse getAllProducts(int pageNo, int pageSize, String sortBy, String sortDir);

}
