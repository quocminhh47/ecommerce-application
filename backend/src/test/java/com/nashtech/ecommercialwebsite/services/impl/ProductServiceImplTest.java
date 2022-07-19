package com.nashtech.ecommercialwebsite.services.impl;

import com.nashtech.ecommercialwebsite.data.entity.Brand;
import com.nashtech.ecommercialwebsite.data.entity.Product;
import com.nashtech.ecommercialwebsite.data.repository.BrandRepository;
import com.nashtech.ecommercialwebsite.data.repository.ProductRepository;
import com.nashtech.ecommercialwebsite.data.repository.RatingRepository;
import com.nashtech.ecommercialwebsite.data.repository.UserRepository;
import com.nashtech.ecommercialwebsite.dto.request.ProductRequest;
import com.nashtech.ecommercialwebsite.dto.response.SingleProductResponse;
import com.nashtech.ecommercialwebsite.exceptions.ResourceNotFoundException;
import com.nashtech.ecommercialwebsite.security.jwt.JwtUtils;
import com.nashtech.ecommercialwebsite.services.JwtService;
import com.nashtech.ecommercialwebsite.services.LoginStatusService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    RatingRepository ratingRepository;
    @Mock
    ProductRepository productRepository;
    @Mock
    ModelMapper mapper;
    @Mock
    JwtUtils jwtUtils;
    @Mock
    JwtService jwtService;
    @Mock
    BrandRepository brandRepository;
    @Mock
    LoginStatusService loginStatusService;
    @InjectMocks
    ProductServiceImpl productServiceImpl;

    Product product;
    Product savedProduct;
    SingleProductResponse expectedSingleProductResponse;
    ProductRequest productRequest;
    Brand brand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userRepository = mock(UserRepository.class);
        ratingRepository = mock(RatingRepository.class);
        productRepository = mock(ProductRepository.class);
        productRepository = mock(ProductRepository.class);
        mapper = mock(ModelMapper.class);
        jwtUtils = mock(JwtUtils.class);
        jwtService = mock(JwtService.class);
        brandRepository = mock(BrandRepository.class);
        loginStatusService = mock(LoginStatusService.class);
//        productServiceImpl = new ProductServiceImpl(
//                userRepository, ratingRepository,
//                jwtService, jwtUtils,
//                productRepository, mapper, brandRepository,
//                loginStatusService);
        product = mock(Product.class);
        savedProduct = mock(Product.class);
        brand = mock(Brand.class);
        productRequest = ProductRequest.builder()
                .name("Dell")
                .price(120L)
                .quantity(1)
                .status("new")
                .description("mo ta")
                .thumbnail("thumbnail")
                .discount(0.1F)
                .guaranteeTime(2)
                .gender(true)
                .isWaterProof(true)
                .size(56F)
                .brandId(1)
                .build();


    }

    //positive case
    @Test
    void givenProductRequest_whenCreateProduct_thenReturnSingleProductResponse() {
        when(mapper.map(productRequest, Product.class)).thenReturn(product);
        when(brandRepository.findById(productRequest.getBrandId())).thenReturn(Optional.of(brand));
        ArgumentCaptor<LocalDateTime> dateTimeCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
        when(productRepository.save(product)).thenReturn(savedProduct);
        when(mapper.map(savedProduct, SingleProductResponse.class)).thenReturn(expectedSingleProductResponse);

        //when
        SingleProductResponse actualProductRes = productServiceImpl.saveProduct(productRequest);

        //then
        //verify(product).setBrand(brand);
        verify(product).setCreatedAt(dateTimeCaptor.capture());
        product.getProductImages().forEach(img -> {
            verify(img).setProduct(product);
        });
        assertThat(actualProductRes, is(expectedSingleProductResponse));
    }

    //nagative case
    @Test
    void givenProductRequestWithInvalidBrandId_whenCreateProduct_thenThrowsException() {
        //given
        when(mapper.map(productRequest, Product.class)).thenReturn(product);
        when(brandRepository.findById(productRequest.getBrandId())).thenReturn(Optional.empty());
        //when
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> productServiceImpl.saveProduct(productRequest));
        //then
        assertThat(exception.getMessage(), is(String.format("Brand not found with id: %s", productRequest.getBrandId())));

    }

    //positive case
    @Test
    void givenProductId_whenDeleteProduct_thenReturnSingleProductResponse() {
        //given
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        Product savedProduct = mock(Product.class);
        when(productRepository.save(product)).thenReturn(savedProduct);
        when(mapper.map(savedProduct, SingleProductResponse.class)).thenReturn(expectedSingleProductResponse);
        //when
        SingleProductResponse acutalResponse = productServiceImpl.deleteProduct(1);
        //then
        verify(product).setHidden(true);
        assertThat(acutalResponse, is(expectedSingleProductResponse));
    }

    //negative case
    @Test
    void givenInvalidProductId_whenDeleteProduct_thenThrowsException() {
        //given
        when(productRepository.findById(1)).thenReturn(Optional.empty());
        //when
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> productServiceImpl.deleteProduct(1));
        //then
        assertThat(exception.getMessage(), is("Product not found with id: 1"));

    }
}