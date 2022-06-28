package com.nashtech.ecommercialwebsite.services;

import com.nashtech.ecommercialwebsite.dto.request.UserRatingRequest;
import com.nashtech.ecommercialwebsite.dto.response.RatingResponse;
import com.nashtech.ecommercialwebsite.dto.response.UserRatingResponse;

public interface RatingService {

   RatingResponse getUserRatingByProduct(int userId, int productId);

   UserRatingResponse rateProduct(UserRatingRequest userRatingRequest);

}
