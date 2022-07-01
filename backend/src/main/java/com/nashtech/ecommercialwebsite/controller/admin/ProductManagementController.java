package com.nashtech.ecommercialwebsite.controller.admin;

import com.nashtech.ecommercialwebsite.dto.request.ProductRequest;
import com.nashtech.ecommercialwebsite.dto.request.ProductUpdateRequest;
import com.nashtech.ecommercialwebsite.dto.response.SingleProductResponse;
import com.nashtech.ecommercialwebsite.services.ProductService;
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
import javax.validation.Valid;

@Tag(name = "Product Resources Management",
        description = "Manage all the product resources, this include CRUD")
@RestController
@RequestMapping("/admin/api/products")
public class ProductManagementController {

    private final ProductService productService;

    public ProductManagementController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "201", description = "CREATED - Successfully created"),
            @ApiResponse( responseCode = "400",
                    description = "Bad Request - The request is invalid",
                    content = {@Content(examples = {@ExampleObject(value = "")})}),
            @ApiResponse( responseCode = "401",
                    description = "Unauthorized -  Authorization information is missing or invalid"),
            @ApiResponse( responseCode = "403",
                    description = "FORBIDDEN - You have no permission to access this resource"),
            @ApiResponse( responseCode = "404",
                    description = "Not found - This product was not found",
                    content = {@Content(examples = {@ExampleObject(value = "")})}),

    })
    public SingleProductResponse saveProduct(@Valid @RequestBody ProductRequest productRequest){
        return productService.saveProduct(productRequest) ;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Change the product information", description = "Provides changing product properties feature")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "OK - Successfully changing"),
            @ApiResponse( responseCode = "400",
                    description = "Bad Request - The request is invalid",
                    content = {@Content(examples = {@ExampleObject(value = "")})}),
            @ApiResponse( responseCode = "401",
                    description = "Unauthorized -  Authorization information is missing or invalid"),
            @ApiResponse( responseCode = "403",
                    description = "FORBIDDEN - You have no permission to access this resource"),
            @ApiResponse( responseCode = "404",
                    description = "Not found - This product was not found",
                    content = {@Content(examples = {@ExampleObject(value = "")})}),

    })
    public SingleProductResponse updateProduct(@PathVariable("id") int id,
                                               @Valid @RequestBody ProductUpdateRequest productRequest) {
        return productService.updateProduct(id, productRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete product", description = "This provide the ability of hide product from the website")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "OK - Successfully delete"),
            @ApiResponse( responseCode = "400",
                    description = "Bad Request - The request is invalid",
                    content = {@Content(examples = {@ExampleObject(value = "")})}),
            @ApiResponse( responseCode = "401",
                    description = "Unauthorized -  Authorization information is missing or invalid"),
            @ApiResponse( responseCode = "403",
                    description = "FORBIDDEN - You have no permission to access this resource"),
            @ApiResponse( responseCode = "404",
                    description = "Not found - This product was not found",
                    content = {@Content(examples = {@ExampleObject(value = "")})}),

    })
    public ResponseEntity<String> deleteProduct(@PathVariable("id") int id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>("DELETED", HttpStatus.OK);
    }
}
