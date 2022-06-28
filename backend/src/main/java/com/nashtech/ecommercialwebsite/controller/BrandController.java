package com.nashtech.ecommercialwebsite.controller;

import com.nashtech.ecommercialwebsite.dto.request.BrandRequest;
import com.nashtech.ecommercialwebsite.dto.response.BrandDto;
import com.nashtech.ecommercialwebsite.dto.response.BrandResponse;
import com.nashtech.ecommercialwebsite.services.BrandService;
import com.nashtech.ecommercialwebsite.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/api/brand")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandDto> getBrandById(@PathVariable("id") int id) {
        return new ResponseEntity<>(brandService.getBrandById(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<BrandResponse> getAllBrands(
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
                    String sortDir
    ) {
        return new ResponseEntity<>(
                brandService.getAllBrands(pageNo, pageSize, sortBy, sortDir),
                HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<BrandDto> createNewBrand(@Valid @RequestBody BrandRequest brandRequest) {
        return new ResponseEntity<>(brandService.save(brandRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrandDto> updateBrand(@PathVariable("id") int id,
                                                @Valid @RequestBody BrandRequest brandRequest) {
        return new ResponseEntity<>(brandService.update(id, brandRequest), HttpStatus.OK);

    }
}
