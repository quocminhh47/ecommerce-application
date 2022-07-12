package com.nashtech.ecommercialwebsite.services.impl;

import com.nashtech.ecommercialwebsite.data.entity.Brand;
import com.nashtech.ecommercialwebsite.data.repository.BrandRepository;
import com.nashtech.ecommercialwebsite.dto.request.BrandRequest;
import com.nashtech.ecommercialwebsite.dto.response.BrandDto;
import com.nashtech.ecommercialwebsite.dto.response.BrandResponse;
import com.nashtech.ecommercialwebsite.dto.response.SingleBrandResponse;
import com.nashtech.ecommercialwebsite.exceptions.ResourceConfictException;
import com.nashtech.ecommercialwebsite.exceptions.ResourceNotFoundException;
import com.nashtech.ecommercialwebsite.services.BrandService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    private final ModelMapper mapper;

    private final LoginStatusServiceImpl loginStatusService;

    @Override
    public SingleBrandResponse getBrandById(int id, HttpServletRequest request) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Brand with ID: %s not found", id)
                ));
        SingleBrandResponse singleBrandResponse = mapper.map(brand, SingleBrandResponse.class);
        singleBrandResponse.setLoginStatusResponse(loginStatusService.getLoginStatus(request));
        return singleBrandResponse;
    }

    @Override
    public BrandResponse getAllBrands(int pageNo,
                                      int pageSize,
                                      String sortBy,
                                      String sortDir,
                                      HttpServletRequest request) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        //create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Brand> brands = brandRepository.findAll(pageable);
        BrandResponse brandResponse = getContent(brands);
        brandResponse.setLoginStatusResponse(loginStatusService.getLoginStatus(request));
        return brandResponse;
    }

    @Override
    public SingleBrandResponse save(BrandRequest brandRequest, HttpServletRequest request) {

        Optional<Brand> optionalBrand = brandRepository.findBrandByName(brandRequest.getName());

        if (optionalBrand.isPresent()) throw new ResourceConfictException(
                String.format("Brand with name: %s is already exist!",
                        brandRequest.getName()));

        Brand savedBrand = brandRepository.save(mapper.map(brandRequest, Brand.class));
        SingleBrandResponse singleBrandResponse = mapper.map(savedBrand, SingleBrandResponse.class);
        singleBrandResponse.setLoginStatusResponse(loginStatusService.getLoginStatus(request));

        return singleBrandResponse;
    }

    @Override
    public SingleBrandResponse update(int id, BrandRequest brandRequest, HttpServletRequest request) {

        Optional<Brand> optionalBrand = brandRepository.findById(id);

        if (optionalBrand.isEmpty()) throw new ResourceNotFoundException(
                String.format("Brand with ID: %s not found!", id));

        Brand brand = optionalBrand.get();
        mapper.map(brandRequest, brand);
        Brand updatedBrand = brandRepository.save(brand);
        SingleBrandResponse brandDto = mapper.map(updatedBrand, SingleBrandResponse.class);
        brandDto.setLoginStatusResponse(loginStatusService.getLoginStatus(request));
        return brandDto;
    }

    private BrandDto mapToDto(Brand brand) {
        return mapper.map(brand, BrandDto.class);
    }

    private BrandResponse getContent(Page<Brand> brands) {
        List<Brand> brandList = brands.getContent();
        List<BrandDto> content = brandList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return BrandResponse.builder()
                .brandContent(content)
                .pageNo(brands.getNumber())
                .pageSize(brands.getSize())
                .totalElements(brands.getTotalElements())
                .totalPages(brands.getTotalPages())
                .last(brands.isLast())
                .build();

    }
}
