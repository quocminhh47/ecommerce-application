package com.nashtech.ecommercialwebsite.services.impl;

import com.nashtech.ecommercialwebsite.data.entity.Brand;
import com.nashtech.ecommercialwebsite.data.repository.BrandRepository;
import com.nashtech.ecommercialwebsite.dto.request.BrandRequest;
import com.nashtech.ecommercialwebsite.dto.response.SingleBrandResponse;
import com.nashtech.ecommercialwebsite.exceptions.ResourceConfictException;
import com.nashtech.ecommercialwebsite.exceptions.ResourceNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BrandServiceImplTest {

    BrandRepository brandRepository;
    ModelMapper modelMapper;
    Brand existingBrand;
    SingleBrandResponse expectedSingleBrandResponse;
    BrandServiceImpl brandServiceImpl;
    BrandRequest brandRequest = new BrandRequest("DELL", "thumbnail", "Mota");


    @BeforeEach
    void setUp() {
        brandRepository = mock(BrandRepository.class);
        modelMapper = mock(ModelMapper.class);
        expectedSingleBrandResponse = mock(SingleBrandResponse.class);
        brandServiceImpl = new BrandServiceImpl(brandRepository, modelMapper);
        existingBrand = mock(Brand.class);

    }

    @Test
    void givenBrandId_whenGetBrandById_thenReturnSingleBrandResponseObject() {
        //given
        when(brandRepository.findById((1))).thenReturn(Optional.of(existingBrand));
        when(modelMapper.map(existingBrand, SingleBrandResponse.class)).thenReturn(expectedSingleBrandResponse);

        //when
        SingleBrandResponse actualSingleBrandResponse = brandServiceImpl.getBrandById(1);

        //then
        Assertions.assertThat(actualSingleBrandResponse).isEqualTo(expectedSingleBrandResponse);
    }

    @Test
    void givenBrandIdNotExist_whenGetBrandById_thenThrowsException() {
        //given
        when(brandRepository.findById((1))).thenReturn(Optional.empty());

        //when
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> brandServiceImpl.getBrandById(1));

        //then
        assertThat(exception.getMessage(), is(String.format("Brand with ID: %s not found", 1)));
    }

    @Test
    void givenBrandRequest_whenSaveBrand_thenReturnSingleBrandResponseObject() {
        //given
        when(brandRepository.findBrandByName("ASUS")).thenReturn(Optional.empty());
        Brand savedBrand = mock(Brand.class);
        when(brandRepository.save(modelMapper.map(brandRequest, Brand.class)))
                .thenReturn(savedBrand);
        SingleBrandResponse expectedResponse = mock(SingleBrandResponse.class);
        when(modelMapper.map(savedBrand, SingleBrandResponse.class))
                .thenReturn(expectedResponse);
        //when
        SingleBrandResponse actualResponse = brandServiceImpl.save(brandRequest);

        //then
        assertThat(actualResponse, is(expectedResponse));
    }

    @Test
    void givenBrandRequestWithExistName_whenSaveBrand_thenThrowsException() {
        //given

        when(brandRepository.findBrandByName(brandRequest.getName()))
                .thenReturn(Optional.of(existingBrand));

        //then
        ResourceConfictException exception = assertThrows(ResourceConfictException.class,
                () -> brandServiceImpl.save(brandRequest));
        //then
        assertThat(exception.getMessage(),
                is(String.format("Brand with name: %s is already exist!", brandRequest.getName())));
    }

    @Test
    void givenBrandRequest_whenUpdateBrand_thenReturnSingleBrandResponse() {
        //given
        when(brandRepository.findById(1)).thenReturn(Optional.of(existingBrand));
        doNothing().when(modelMapper).map(brandRequest, existingBrand);
        Brand updatedBrand = mock(Brand.class);
        when(brandRepository.save(existingBrand)).thenReturn(updatedBrand);
        when(modelMapper.map(updatedBrand, SingleBrandResponse.class))
                .thenReturn(expectedSingleBrandResponse);
        //when
        SingleBrandResponse actualResponse = brandServiceImpl.update(1, brandRequest);
        //then
        verify(modelMapper).map(brandRequest, existingBrand);
        assertThat(actualResponse, is(expectedSingleBrandResponse));
    }

    @Test
    void givenBrandRequestWithExistName_whenUpdateBrand_thenThrowsException() {
        //given
        when(brandRepository.findById(1)).thenReturn(Optional.empty());
        //when
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> brandServiceImpl.update(1, brandRequest));
        //then
        assertThat(exception.getMessage(), is(String.format("Brand with ID %s not found", 1)));

    }


}