package com.nashtech.ecommercialwebsite.services;

import com.nashtech.ecommercialwebsite.dto.request.BrandRequest;
import com.nashtech.ecommercialwebsite.dto.response.BrandDto;
import com.nashtech.ecommercialwebsite.dto.response.BrandResponse;
import com.nashtech.ecommercialwebsite.dto.response.SingleBrandResponse;

import javax.servlet.http.HttpServletRequest;

public interface BrandService {

    SingleBrandResponse getBrandById(int id, HttpServletRequest request);

    BrandResponse getAllBrands(int pageNo,
                               int pageSize,
                               String sortBy,
                               String sortDir,
                               HttpServletRequest request);

    SingleBrandResponse save(BrandRequest brandRequest, HttpServletRequest request);

    SingleBrandResponse update(int id, BrandRequest brandRequest, HttpServletRequest request);

}
