package com.nashtech.ecommercialwebsite.services;

import com.nashtech.ecommercialwebsite.dto.request.UserRatingRequest;
import com.nashtech.ecommercialwebsite.dto.response.RatingResponse;
import com.nashtech.ecommercialwebsite.dto.response.UserRatingResponse;

import javax.servlet.http.HttpServletRequest;

public interface RatingService {

    RatingResponse getUserRatingByProduct(int productId);

    UserRatingResponse rateProduct(UserRatingRequest userRatingRequest);

}
