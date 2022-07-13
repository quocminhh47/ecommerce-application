package com.nashtech.ecommercialwebsite.controller.admin;

import com.nashtech.ecommercialwebsite.dto.request.BrandRequest;
import com.nashtech.ecommercialwebsite.dto.response.BrandResponse;
import com.nashtech.ecommercialwebsite.dto.response.SingleBrandResponse;
import com.nashtech.ecommercialwebsite.services.BrandService;
import com.nashtech.ecommercialwebsite.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Tag(name = "Brands Resources Management",
        description = "Permit to access / change all the available brands")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/admin/api/brands")
public class BrandManagementController {

    private final BrandService brandService;

    public BrandManagementController(BrandService brandService) {
        this.brandService = brandService;
    }

    @Operation(summary = "Get brand information by ID",
            description = "Provide all information about the specified brand")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "OK - Successfully retrieved"),
            @ApiResponse( responseCode = "401",
                    description = "Unauthorized -  Authorization information is missing or invalid"),
            @ApiResponse( responseCode = "403",
                    description = "FORBIDDEN - You have no permission to access this resource"),
            @ApiResponse( responseCode = "404",
                    description = "Not found - The brand resources was not found",
                    content = {@Content(examples = {@ExampleObject(value = "")})})
    })
    @GetMapping("/{id}")
    public ResponseEntity<SingleBrandResponse> getBrandById(@PathVariable("id") int id,
                                                            HttpServletRequest request) {
        return new ResponseEntity<>(brandService.getBrandById(id, request), HttpStatus.OK);
    }


 @Operation(summary = "Get all brands", description = "This return all the available brands in pagination")
 @ApiResponses(value = {
         @ApiResponse( responseCode = "200", description = "OK - Successfully retrieved"),
         @ApiResponse( responseCode = "401",
                 description = "Unauthorized -  Authorization information is missing or invalid"),
         @ApiResponse( responseCode = "403",
                 description = "FORBIDDEN - You have no permission to access this resource"),
         @ApiResponse( responseCode = "404",
                 description = "Not found - The brand resources was not found",
                 content = {@Content(examples = {@ExampleObject(value = "")})})
 })
    @GetMapping()
    public ResponseEntity<BrandResponse> getAllBrands(
            @RequestParam(
                    value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false)
                    int pageNo,
            @RequestParam(
                    value = "pageSize", defaultValue = "7" , required = false)
                    int pageSize,
            @RequestParam(
                    value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false)
                    String sortBy,
            @RequestParam(
                    value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false)
                    String sortDir,
            HttpServletRequest request
    ) {
        return new ResponseEntity<>(
                brandService.getAllBrands(pageNo, pageSize, sortBy, sortDir, request),
                HttpStatus.OK);
    }

    @PostMapping()
    @Operation(summary = "Create new brand", description = "Fill the information to create new brand ")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "201", description = "CREATED - Successfully created a new brand"),
            @ApiResponse( responseCode = "401",
                    description = "Unauthorized -  Authorization information is missing or invalid"),
            @ApiResponse( responseCode = "403",
                    description = "FORBIDDEN - You have no permission to access this resource"),
            @ApiResponse( responseCode = "404",
                    description = "Not found - The request resources was not found",
                    content = {@Content(examples = {@ExampleObject(value = "")})})
    })
    public ResponseEntity<SingleBrandResponse> createNewBrand(@Valid @RequestBody BrandRequest brandRequest,
                                                   HttpServletRequest request) {
        return new ResponseEntity<>(brandService.save(brandRequest, request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Change brand information"
            , description = "Changing the properties of a specified brand")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200",
                    description = "OK - Successfully changed the information of a brand"),
            @ApiResponse( responseCode = "401",
                    description = "Unauthorized -  Authorization information is missing or invalid"),
            @ApiResponse( responseCode = "403",
                    description = "FORBIDDEN - You have no permission to access this resource"),
            @ApiResponse( responseCode = "404",
                    description = "Not found - The request resources was not found",
                    content = {@Content(examples = {@ExampleObject(value = "")})})
    })
    public ResponseEntity<SingleBrandResponse> updateBrand(@PathVariable("id") int id,
                                                @Valid @RequestBody BrandRequest brandRequest,
                                                HttpServletRequest request) {
        return new ResponseEntity<>(brandService.update(id, brandRequest, request), HttpStatus.OK);

    }
}
