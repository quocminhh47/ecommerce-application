package com.nashtech.ecommercialwebsite.services;


import com.nashtech.ecommercialwebsite.dto.request.ProductRequest;
import com.nashtech.ecommercialwebsite.dto.request.ProductUpdateRequest;
import com.nashtech.ecommercialwebsite.dto.response.ProductResponse;
import com.nashtech.ecommercialwebsite.dto.response.SingleProductResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public interface ProductService {

    SingleProductResponse findProductById(int id);

    ProductResponse getAllProducts(int pageNo,
                                   int pageSize,
                                   String sortBy,
                                   String sortDirection);

    ProductResponse getAllAvailableProducts( boolean hidden ,
                                             int pageNo,
                                             int pageSize,
                                             String sortBy,
                                             String sortDirection);

    ProductResponse getProductsByBrandName(String brandName,
                                           int pageNo,
                                           int pageSize,
                                           String sortBy,
                                           String sortDirection);

    SingleProductResponse saveProduct(ProductRequest productRequest);

    SingleProductResponse updateProduct(int id , ProductUpdateRequest productRequest);

    SingleProductResponse deleteProduct(int id);

    ProductResponse getProductsByGender(boolean gender,
                                        int pageNo,
                                        int pageSize,
                                        String sortBy,
                                        String sortDirection);


}
