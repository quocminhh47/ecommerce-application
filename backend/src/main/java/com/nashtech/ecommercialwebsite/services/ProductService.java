package com.nashtech.ecommercialwebsite.services;


import com.nashtech.ecommercialwebsite.dto.request.ProductRequest;
import com.nashtech.ecommercialwebsite.dto.request.ProductUpdateRequest;
import com.nashtech.ecommercialwebsite.dto.response.ProductResponse;
import com.nashtech.ecommercialwebsite.dto.response.SingleProductResponse;

import javax.servlet.http.HttpServletRequest;


public interface ProductService {

    SingleProductResponse findProductById(int id, HttpServletRequest request);
//    SingleProductResponse findProductById(int id);

    ProductResponse getAllProducts(int pageNo,
                                   int pageSize,
                                   String sortBy,
                                   String sortDirection,
                                   HttpServletRequest request);

    ProductResponse getAllAvailableProducts( boolean hidden ,
                                             int pageNo,
                                             int pageSize,
                                             String sortBy,
                                             String sortDirection,
                                             HttpServletRequest request);

    ProductResponse getProductsByBrandName(String brandName,
                                           int pageNo,
                                           int pageSize,
                                           String sortBy,
                                           String sortDirection,
                                           HttpServletRequest request);

    SingleProductResponse saveProduct(ProductRequest productRequest);

    SingleProductResponse updateProduct(int id , ProductUpdateRequest productRequest);

    void deleteProduct(int id);


}
