package com.nashtech.ecommercialwebsite.controller.user;

import com.nashtech.ecommercialwebsite.dto.response.FileUploadResponse;
import com.nashtech.ecommercialwebsite.dto.response.ProductResponse;
import com.nashtech.ecommercialwebsite.dto.response.SingleProductResponse;
import com.nashtech.ecommercialwebsite.services.CloudinaryService;
import com.nashtech.ecommercialwebsite.services.ProductService;
import com.nashtech.ecommercialwebsite.services.impl.CloudinaryServiceimpl;
import com.nashtech.ecommercialwebsite.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Product Resources",
    description = "Product resources that provide access to all available products")
@RestController
@RequestMapping("/user/api/products")
public class ProductController {

    private final ProductService productService;
    private final CloudinaryService cloudinaryService;

    public ProductController(ProductService productService, CloudinaryServiceimpl cloudinaryService) {
        this.productService = productService;
        this.cloudinaryService = cloudinaryService;
    }


    @GetMapping()
    @Operation(summary = "Get products list", description = "Provides all products in pagination")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "OK - Successfully retrieved"),
            @ApiResponse( responseCode = "400",
                    description = "Bad Request - The request is invalid",
                    content = {@Content(examples = {@ExampleObject(value = "")})}),
            @ApiResponse( responseCode = "404",
                    description = "Not found - The product list was not found",
                    content = {@Content(examples = {@ExampleObject(value = "")})}),

    })
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
    //@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Get product detail by ID", description = "Provides product information by single")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "OK - Successfully retrieved"),
            @ApiResponse( responseCode = "400",
                    description = "Bad Request - The request is invalid",
                    content = {@Content(examples = {@ExampleObject(value = "")})}),
            @ApiResponse( responseCode = "404",
                    description = "Not found - This product was not found",
                    content = {@Content(examples = {@ExampleObject(value = "")})}),

    })
    public ResponseEntity<SingleProductResponse> findProductById(@PathVariable("id") int id) {
        return new ResponseEntity<>(productService.findProductById(id), HttpStatus.OK);
    }

    @GetMapping("/brand/{brandName}")
    //@PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Filter products by brand name", description = "Provides all products by brand in pagination")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "OK - Successfully retrieved"),
            @ApiResponse( responseCode = "400",
                    description = "Bad Request - The request is invalid",
                    content = {@Content(examples = {@ExampleObject(value = "")})}),
            @ApiResponse( responseCode = "404",
                    description = "Not found - The product list was not found",
                    content = {@Content(examples = {@ExampleObject(value = "")})}),

    })
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
    @Operation(summary = "Upload image to cloudinary", description = "This provide the ability of upload image to Cloudinary and return the URL")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "201", description = "OK - Successfully uploading image"),
            @ApiResponse( responseCode = "400",
                    description = "Bad Request - The file media is invalid",
                    content = {@Content(examples = {@ExampleObject(value = "")})}),
            @ApiResponse( responseCode = "404",
                    description = "Not found - The request resources was not found",
                    content = {@Content(examples = {@ExampleObject(value = "")})})
    })
    public FileUploadResponse upLoad(
            @RequestParam(value = "file", required = true) MultipartFile multipartFile) {
        return cloudinaryService.upload(multipartFile);
    }


}