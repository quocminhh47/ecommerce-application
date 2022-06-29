package com.nashtech.ecommercialwebsite.controller.user;

import com.nashtech.ecommercialwebsite.dto.response.FileUploadResponse;
import com.nashtech.ecommercialwebsite.dto.response.ProductResponse;
import com.nashtech.ecommercialwebsite.dto.response.SingleProductResponse;
import com.nashtech.ecommercialwebsite.services.CloudinaryService;
import com.nashtech.ecommercialwebsite.services.ProductService;
import com.nashtech.ecommercialwebsite.services.impl.CloudinaryServiceimpl;
import com.nashtech.ecommercialwebsite.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/user/api/products")
public class ProductController {

    private final ProductService productService;
    private final CloudinaryService cloudinaryService;

    public ProductController(ProductService productService, CloudinaryServiceimpl cloudinaryService) {
        this.productService = productService;
        this.cloudinaryService = cloudinaryService;
    }

    //get all available product (hidden = false)
    @GetMapping()
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(
                    value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false  )
                    int pageNo,
            @RequestParam(
                    value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false)
                    int pageSize,
            @RequestParam(
                    value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false)
                    String sortBy,
            @RequestParam(
                    value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false)
                    String sortDir ) {

        return new
                    ResponseEntity<>(
                    productService.getAllAvailableProducts( false,pageNo, pageSize, sortBy, sortDir),
                    HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<SingleProductResponse> findProductById(@PathVariable("id") int id) {
        return new ResponseEntity<>(productService.findProductById(id), HttpStatus.OK);
    }

    @GetMapping("/{brandName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> getAllProductsByBrand(
            @RequestParam(
                    value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false)
                    int pageNo,
            @RequestParam(
                    value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false)
                    int pageSize,
            @RequestParam(
                    value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false)
                    String sortBy,
            @RequestParam(
                    value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false)
                    String sortDir,
            @PathVariable("brandName") String brandName ) {

        return new ResponseEntity<>( productService.getProductsByBrandName(brandName.toUpperCase(), pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @PostMapping("/gallery")
    @ResponseStatus(HttpStatus.CREATED)
    public FileUploadResponse upLoad(
            @RequestParam(value = "file", required = true) MultipartFile multipartFile) {
        return cloudinaryService.upload(multipartFile);
    }


}
