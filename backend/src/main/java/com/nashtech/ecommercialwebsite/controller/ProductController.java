package com.nashtech.ecommercialwebsite.controller;

import com.nashtech.ecommercialwebsite.dto.request.ProductRequest;
import com.nashtech.ecommercialwebsite.dto.request.ProductUpdateRequest;
import com.nashtech.ecommercialwebsite.dto.response.ProductResponse;
import com.nashtech.ecommercialwebsite.dto.response.SingleProductResponse;
import com.nashtech.ecommercialwebsite.services.ProductService;
import com.nashtech.ecommercialwebsite.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return new ResponseEntity<>( productService.getAllProducts(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<SingleProductResponse> findProductById(@PathVariable("id") int id) {
        return new ResponseEntity<>(productService.findProductById(id), HttpStatus.OK);
    }

    @GetMapping("/product-{brandName}")
    public ResponseEntity<ProductResponse> getAllProductsByBrand(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
            @PathVariable("brandName") String brandName
    ) {
        return new ResponseEntity<>( productService.getProductsByBrandName(brandName.toUpperCase(), pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @PostMapping("/product")
    @ResponseStatus(HttpStatus.CREATED)
    public SingleProductResponse saveProduct( @Valid @RequestBody ProductRequest productRequest){
        return productService.saveProduct(productRequest) ;
    }

    @PutMapping("product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SingleProductResponse updateProduct(@PathVariable("id") int id,
                                               @Valid @RequestBody ProductUpdateRequest productRequest) {
        return productService.updateProduct(id, productRequest);
    }

    @DeleteMapping("product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") int id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>("DELETED", HttpStatus.OK);
    }


}
