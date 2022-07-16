package com.nashtech.ecommercialwebsite.services.impl;

import com.nashtech.ecommercialwebsite.data.entity.Product;
import com.nashtech.ecommercialwebsite.data.repository.BrandRepository;
import com.nashtech.ecommercialwebsite.data.repository.ProductRepository;
import com.nashtech.ecommercialwebsite.data.repository.RatingRepository;
import com.nashtech.ecommercialwebsite.data.repository.UserRepository;
import com.nashtech.ecommercialwebsite.dto.response.RatingResponse;
import com.nashtech.ecommercialwebsite.dto.response.SingleBrandResponse;
import com.nashtech.ecommercialwebsite.dto.response.SingleProductResponse;
import com.nashtech.ecommercialwebsite.security.jwt.JwtUtils;
import com.nashtech.ecommercialwebsite.services.JwtService;
import com.nashtech.ecommercialwebsite.services.LoginStatusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static org.mockito.Mockito.*;

class ProductServiceImplTest {
    @Mock UserRepository userRepository;
    @Mock RatingRepository ratingRepository;
    @Mock ProductRepository productRepository;
    @Mock ModelMapper mapper;
    @Mock JwtUtils jwtUtils;
    @Mock JwtService jwtService;
    @Mock BrandRepository brandRepository;
    @Mock LoginStatusService loginStatusService;
    @InjectMocks
    ProductServiceImpl productServiceImpl;

    Product product ;
    SingleProductResponse expectedSingleProductResponse;

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
        productServiceImpl = new ProductServiceImpl(
                userRepository, ratingRepository,
                jwtService, jwtUtils,
                productRepository, mapper, brandRepository,
                loginStatusService);
        product = mock(Product.class);
    }

    @Test
    void givenProductIdAndRequest_whenFindProductById_thenReturnSinglePRoductResponse() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        Product product = mock(Product.class);
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(mapper.map(product, SingleProductResponse.class)).thenReturn(expectedSingleProductResponse);

        ArgumentCaptor<RatingResponse> ratingCaptor = ArgumentCaptor.forClass(RatingResponse.class);

        //when
        SingleProductResponse actualResponse = productServiceImpl.findProductById(1, request );

        //then
        verify(expectedSingleProductResponse).setProductImages(product.getProductImages());
//        assertThat()
    }
}