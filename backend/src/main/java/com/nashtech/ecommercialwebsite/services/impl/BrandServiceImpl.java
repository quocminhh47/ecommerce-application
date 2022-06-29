package com.nashtech.ecommercialwebsite.services.impl;

import com.nashtech.ecommercialwebsite.data.entity.Brand;
import com.nashtech.ecommercialwebsite.data.repository.BrandRepository;
import com.nashtech.ecommercialwebsite.dto.request.BrandRequest;
import com.nashtech.ecommercialwebsite.dto.response.BrandDto;
import com.nashtech.ecommercialwebsite.dto.response.BrandResponse;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    private final ModelMapper mapper;

    @Override
    public BrandDto getBrandById(int id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Brand with ID: %s not found", id)
                ));
        return mapper.map(brand, BrandDto.class);
    }

    @Override
    public BrandResponse getAllBrands(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        //create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Brand> brands = brandRepository.findAll(pageable);
        return getContent(brands);
    }

    @Override
    public BrandDto save(BrandRequest brandRequest) {

        Optional<Brand> optionalBrand = brandRepository.findBrandByName(brandRequest.getName());

        if(optionalBrand.isPresent()) throw new ResourceConfictException(
                String.format("Brand with name: %s is already exist!",
                        brandRequest.getName()));

        Brand savedBrand= brandRepository.save(mapper.map(brandRequest, Brand.class));
        return mapper.map(savedBrand, BrandDto.class);
    }

    @Override
    public BrandDto update(int id, BrandRequest brandRequest) {

        Optional<Brand> optionalBrand = brandRepository.findById(id);

        if(optionalBrand.isEmpty()) throw new ResourceNotFoundException(
                String.format("Brand with ID: %s not found!",id ));

        Brand brand = optionalBrand.get();
        mapper.map(brandRequest, brand);
        Brand updatedBrand = brandRepository.save(brand);
        return mapper.map(updatedBrand, BrandDto.class);
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
