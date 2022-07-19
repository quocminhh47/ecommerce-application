package com.nashtech.ecommercialwebsite.controller.admin;

import com.nashtech.ecommercialwebsite.dto.request.ProductRequest;
import com.nashtech.ecommercialwebsite.dto.request.ProductUpdateRequest;
import com.nashtech.ecommercialwebsite.dto.response.FileUploadResponse;
import com.nashtech.ecommercialwebsite.dto.response.ProductResponse;
import com.nashtech.ecommercialwebsite.dto.response.SingleProductResponse;
import com.nashtech.ecommercialwebsite.services.CloudinaryService;
import com.nashtech.ecommercialwebsite.services.ProductService;
import com.nashtech.ecommercialwebsite.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;

@Tag(name = "Product Resources Management",
        description = "Manage all the product resources, this include CRUD")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/admin/api/products")
public class ProductManagementController {

    private final ProductService productService;
    private final CloudinaryService cloudinaryService;

    public ProductManagementController(ProductService productService, CloudinaryService cloudinaryService) {
        this.productService = productService;
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED - Successfully created"),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request - The request is invalid",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized -  Authorization information is missing or invalid"),
            @ApiResponse(responseCode = "403",
                    description = "FORBIDDEN - You have no permission to access this resource"),
            @ApiResponse(responseCode = "404",
                    description = "Not found - This product was not found",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "500",
                    description = "Internal Error - There were some error while processing in server",
                    content = {@Content(examples = {@ExampleObject()})})

    })
    public SingleProductResponse saveProduct(@Valid @RequestBody ProductRequest productRequest) {
        return productService.saveProduct(productRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Change the product information", description = "Provides changing product properties feature")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Successfully changing"),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request - The request is invalid",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized -  Authorization information is missing or invalid"),
            @ApiResponse(responseCode = "403",
                    description = "FORBIDDEN - You have no permission to access this resource"),
            @ApiResponse(responseCode = "404",
                    description = "Not found - This product was not found",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "500",
                    description = "Internal Error - There were some error while processing in server",
                    content = {@Content(examples = {@ExampleObject()})})

    })
    public SingleProductResponse updateProduct(@PathVariable("id") int id,
                                               @Valid @RequestBody ProductUpdateRequest productRequest) {
        return productService.updateProduct(id, productRequest);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product", description = "This provide the ability of hide product from the website")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Successfully delete"),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request - The request is invalid",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized -  Authorization information is missing or invalid"),
            @ApiResponse(responseCode = "403",
                    description = "FORBIDDEN - You have no permission to access this resource"),
            @ApiResponse(responseCode = "404",
                    description = "Not found - This product was not found",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "500",
                    description = "Internal Error - There were some error while processing in server",
                    content = {@Content(examples = {@ExampleObject()})})

    })
    public SingleProductResponse deleteProduct(@PathVariable("id") int id) {
        return productService.deleteProduct(id);
    }

    @PostMapping("/gallery")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Upload image to cloudinary", description = "This provide the ability of upload image to Cloudinary and return the URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK - Successfully uploading image"),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request - The file media is invalid",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "404",
                    description = "Not found - The request resources was not found",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "500",
                    description = "Internal Error - There were some error while processing in server",
                    content = {@Content(examples = {@ExampleObject()})})
    })
    public FileUploadResponse upLoad( @RequestParam(value = "file") MultipartFile multipartFile) {
        return cloudinaryService.upload(multipartFile);
    }

    @GetMapping()
    @Operation(summary = "Get products list by pagination", description = "Provides all products in pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Successfully retrieved"),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request - The request is invalid",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "404",
                    description = "Not found - The product list was not found",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "500",
                    description = "Internal Error - There were some error while processing in server",
                    content = {@Content(examples = {@ExampleObject()})})
    })
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getAllProducts(
            @RequestParam(
                    value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false)
                    int pageNo,
            @RequestParam(
                    value = "pageSize", defaultValue = "7", required = false)
                    int pageSize,
            @RequestParam(
                    value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false)
                    String sortBy,
            @RequestParam(
                    value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false)
                    String sortDir) {
        return productService.getAllProducts(pageNo, pageSize, sortBy, sortDir);
    }
}
