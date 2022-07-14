package com.nashtech.ecommercialwebsite.services;

import com.nashtech.ecommercialwebsite.dto.request.BrandRequest;
import com.nashtech.ecommercialwebsite.dto.response.BrandResponse;
import com.nashtech.ecommercialwebsite.dto.response.SingleBrandResponse;

public interface BrandService {

    SingleBrandResponse getBrandById(int id);

    BrandResponse getAllBrands(int pageNo,
                               int pageSize,
                               String sortBy,
                               String sortDir);

    SingleBrandResponse save(BrandRequest brandRequest);

    SingleBrandResponse update(int id, BrandRequest brandRequest);

}
