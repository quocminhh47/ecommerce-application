package com.nashtech.ecommercialwebsite.service;


import com.nashtech.ecommercialwebsite.data.entity.Product;
import com.nashtech.ecommercialwebsite.dto.request.ProductRequest;
import com.nashtech.ecommercialwebsite.dto.request.ProductUpdateRequest;
import com.nashtech.ecommercialwebsite.dto.response.ProductResponse;
import com.nashtech.ecommercialwebsite.dto.response.SingleProductResponse;



public interface ProductService {

    SingleProductResponse findProductById(int id);

    ProductResponse getAllProducts(int pageNo, int pageSize, String sortBy, String sortDir);

    ProductResponse getProductsByBrandName(String brandName, int pageNo, int pageSize, String sortBy, String sortDir);

    SingleProductResponse saveProduct(ProductRequest productRequest);

    SingleProductResponse updateProduct(int id , ProductUpdateRequest productRequest);

    void deleteProduct(int id);


}
