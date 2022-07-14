package com.nashtech.ecommercialwebsite.services.impl;

import com.nashtech.ecommercialwebsite.data.entity.Brand;
import com.nashtech.ecommercialwebsite.data.repository.BrandRepository;
import com.nashtech.ecommercialwebsite.dto.request.BrandRequest;
import com.nashtech.ecommercialwebsite.dto.response.SingleBrandResponse;
import com.nashtech.ecommercialwebsite.exceptions.ResourceConfictException;
import com.nashtech.ecommercialwebsite.exceptions.ResourceNotFoundException;
import org.hamcrest.MatcherAssert;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Assertions;

class BrandServiceImplTest {

    BrandServiceImpl brandServiceImpl;
    BrandRepository brandRepository;
    ModelMapper mapper;
    BrandRequest brandRequest;
    Brand brandMappered; //brand has been mappered to from brandRequest
    Brand savedBrand;
    Brand existingBrand; //in case brand name's already exist
    SingleBrandResponse expectedBrandResponse;


    @BeforeEach
    void setUp() {
        brandRepository = mock(BrandRepository.class);
        mapper = mock(ModelMapper.class);
        brandRequest = mock(BrandRequest.class);
        brandMappered = mock(Brand.class);
        savedBrand = mock(Brand.class);
        brandServiceImpl = new BrandServiceImpl(brandRepository, mapper);
        expectedBrandResponse = mock(SingleBrandResponse.class);
        existingBrand = mock(Brand.class);

        brandRequest = BrandRequest.builder()
                .name("ASUS")
                .thumbnail("thumbnail")
                .description("Dell is good")
                .build();

        existingBrand = Brand.builder()
                .id(1)
                .name("MOCKITO")
                .thumbnail("thumbnail 2")
                .description("description")
                .build();
    }

    //TODO: check
    @Disabled
    @DisplayName("Junit test for save brand method")
    @Test
    void givenBrandRequest_whenSaveBrand_thenReturnSingleBrandResponse() {
        //given
        given(brandRepository.findBrandByName(brandRequest.getName()))
                .willReturn(Optional.empty());

        given(mapper.map(brandRequest, Brand.class)).willReturn(brandMappered);

        given(brandRepository.save(brandMappered)).willReturn(savedBrand);

        given(mapper.map(savedBrand, SingleBrandResponse.class))
                .willReturn(expectedBrandResponse);

        //when
        SingleBrandResponse singleBrandResponse = brandServiceImpl.save(brandRequest);

        //then
        org.assertj.core.api.Assertions.assertThat(expectedBrandResponse).isEqualTo(singleBrandResponse);

    }

    @DisplayName("Junit test for save brand method with throw exception")
    @Test
    void givenBrandRequestWithExistingBrandName_whenSaveBrand_thenThrowsException() {
        //given
        existingBrand = mock(Brand.class);

        given(brandRepository.findBrandByName(brandRequest.getName()))
                .willReturn(Optional.of(existingBrand));

        //when
        ResourceConfictException exception = Assertions.assertThrows(ResourceConfictException.class, () -> {
            brandServiceImpl.save(brandRequest);
        });

        // then
        MatcherAssert.assertThat(exception.getMessage(),
                is(String.format("Brand with name: %s is already exist!", brandRequest.getName() )));

    }


    @DisplayName("Junit test for get brand by ID (positive scenario)")
    @Test
    void givenBrandId_whenGetBrandById_thenReturnSingleBrandResponseObject() {
        //given
        given(brandRepository.findById(1)).willReturn(Optional.of(existingBrand));

        given(mapper.map(existingBrand, SingleBrandResponse.class)).willReturn(expectedBrandResponse);

        //when
        SingleBrandResponse actualBrandResponse = brandServiceImpl.getBrandById(1);

        //then
        assertThat(actualBrandResponse).isNotNull();
        assertThat(actualBrandResponse).isEqualTo(expectedBrandResponse);
    }

    @DisplayName("Junit test for get brand by ID (negative scenario)")
    @Test
    void givenInvalidBrandId_whenGetBrandById_thenThrowsException() {
        //given
        given(brandRepository.findById(1)).willReturn(Optional.empty());

        //when
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class,() -> {
            brandServiceImpl.getBrandById(1);
        });

        //then
       MatcherAssert.assertThat(exception.getMessage(), is(String.format("Brand with ID: %s not found", 1)));
    }


    @DisplayName("Junit test update brand (positive scenario)")
    @Test
    void givenBrandRequest_whenUpdateBrand_thenReturnSingleBrandResponse() {

        //given
        given(brandRepository.findById(1)).willReturn(Optional.of(existingBrand));

//        existingBrand.setName(brandRequest.getName());
//        existingBrand.setThumbnail(brandRequest.getThumbnail());
//        existingBrand.setDescription(brandRequest.getDescription());
//        System.out.println(existingBrand.toString());
        willDoNothing().given(mapper).map(brandRequest, existingBrand);

        given(brandRepository.save(existingBrand)).willReturn(savedBrand);

        given(mapper.map(savedBrand, SingleBrandResponse.class)).willReturn(expectedBrandResponse);

        //when
        SingleBrandResponse actualBrandResponse = brandServiceImpl.update(1, brandRequest);

        //then
        assertThat(existingBrand).isEqualTo(savedBrand);
        assertThat(actualBrandResponse).isEqualTo(expectedBrandResponse);
//        assertThat(actualBrandResponse.getName()).isEqualTo(brandRequest.getName());
//        assertThat(actualBrandResponse.getThumbnail()).isEqualTo(brandRequest.getThumbnail());
//        assertThat(actualBrandResponse.getDescription()).isEqualTo(brandRequest.getDescription());
    }


    @DisplayName("Junit test update brand (negative scenario)")
    @Test
    void givenInvalidId_whenUpdateBrand_thenThrowsException() {

        //given
        given(brandRepository.findById(1)).willReturn(Optional.empty());

        //when
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> brandServiceImpl.update(1, brandRequest));

        //then

        MatcherAssert.assertThat(exception.getMessage(),
                is( String.format("Brand with ID: %s not found!", 1)));
    }
}