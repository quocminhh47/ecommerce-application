package com.nashtech.ecommercialwebsite.services;

import com.nashtech.ecommercialwebsite.dto.request.BrandRequest;
import com.nashtech.ecommercialwebsite.dto.response.BrandDto;
import com.nashtech.ecommercialwebsite.dto.response.BrandResponse;

public interface BrandService {

    BrandDto  getBrandById(int id);

    BrandResponse getAllBrands(int pageNo, int pageSize, String sortBy, String sortDir);

    BrandDto save(BrandRequest brandRequest);

    BrandDto update(int id, BrandRequest brandRequest);

}
