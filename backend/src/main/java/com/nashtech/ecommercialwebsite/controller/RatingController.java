package com.nashtech.ecommercialwebsite.controller;

import com.nashtech.ecommercialwebsite.dto.request.UserRatingRequest;
import com.nashtech.ecommercialwebsite.dto.response.RatingResponse;
import com.nashtech.ecommercialwebsite.dto.response.UserRatingResponse;
import com.nashtech.ecommercialwebsite.services.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/ratings")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RatingResponse> getRatingInfo(
            @RequestParam("user-id") int userId,
            @RequestParam("product-id") int productId) {
        return new ResponseEntity<>(ratingService.getUserRatingByProduct(userId, productId), HttpStatus.OK);
    }

    @PostMapping("/ratings")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserRatingResponse> rateProduct(@Valid @RequestBody UserRatingRequest userRatingRequest){
        return new ResponseEntity<>(ratingService.rateProduct(userRatingRequest), HttpStatus.CREATED);
    }
}
