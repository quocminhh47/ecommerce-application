package com.nashtech.ecommercialwebsite.services.impl;

import com.nashtech.ecommercialwebsite.data.entity.Account;
import com.nashtech.ecommercialwebsite.data.entity.Product;
import com.nashtech.ecommercialwebsite.data.repository.ProductRepository;
import com.nashtech.ecommercialwebsite.data.repository.RatingRepository;
import com.nashtech.ecommercialwebsite.data.repository.UserRepository;
import com.nashtech.ecommercialwebsite.dto.request.UserRatingRequest;
import com.nashtech.ecommercialwebsite.dto.response.RatingResponse;
import com.nashtech.ecommercialwebsite.security.jwt.JwtUtils;
import com.nashtech.ecommercialwebsite.services.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import javax.servlet.http.HttpServletRequest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RatingServiceImplTest {

    @Mock  JwtService jwtService;
    @Mock  JwtUtils jwtUtils;
    @Mock  RatingRepository ratingRepository;
    @Mock  ProductRepository productRepository;
    @Mock  UserRepository userRepository;
    @Mock  ModelMapper mapper;
    @InjectMocks RatingServiceImpl ratingServiceImpl;
    HttpServletRequest request;
    Account account;
    UserRatingRequest ratingRequest;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = mock(HttpServletRequest.class);
        account = mock(Account.class);
        ratingRequest = new UserRatingRequest(1,1);
    }

//    @Test
//    void givenProductIdAndHttpRequest_whenGetUserRatingInfo_thenReturnRatingResponse() {
//        //given
//        when(jwtService.parseJwt(request)).thenReturn("BearerToken");
//        when(jwtService.getUsernameFromToken("BearerToken")).thenReturn("username");
//        when(userRepository.findAccountByUsername("username")).thenReturn(Optional.of(account));
//        when(ratingRepository.getRatingPointsFromProduct(1)).thenReturn(1D);
//        when(account.getId()).thenReturn(1);
//        when(ratingRepository.getUserRatingPointsByProduct(account.getId(),1)).thenReturn(1);
//
//        //when
//        RatingResponse actualResponse = ratingServiceImpl.getUserRatingByProduct(1, request);
//
//        //then
//        assertThat(actualResponse.getProductRatingPoints()).isEqualTo(1D);
//        assertThat(actualResponse.getUserRatingPoints()).isEqualTo(1);
//    }

}