package com.nashtech.ecommercialwebsite.services;

import com.nashtech.ecommercialwebsite.dto.request.UserRatingRequest;
import com.nashtech.ecommercialwebsite.dto.response.RatingResponse;
import com.nashtech.ecommercialwebsite.dto.response.UserRatingResponse;

import javax.servlet.http.HttpServletRequest;

public interface RatingService {

    RatingResponse getUserRatingByProduct(int productId, HttpServletRequest request);

    UserRatingResponse rateProduct(UserRatingRequest userRatingRequest, HttpServletRequest request);

}
