package com.nashtech.ecommercialwebsite.services.impl;

import com.nashtech.ecommercialwebsite.data.entity.Account;
import com.nashtech.ecommercialwebsite.data.entity.Product;
import com.nashtech.ecommercialwebsite.data.entity.Rating;
import com.nashtech.ecommercialwebsite.data.repository.ProductRepository;
import com.nashtech.ecommercialwebsite.data.repository.RatingRepository;
import com.nashtech.ecommercialwebsite.data.repository.UserRepository;
import com.nashtech.ecommercialwebsite.dto.request.UserRatingRequest;
import com.nashtech.ecommercialwebsite.dto.response.RatingResponse;
import com.nashtech.ecommercialwebsite.dto.response.UserRatingResponse;
import com.nashtech.ecommercialwebsite.exceptions.ResourceNotFoundException;
import com.nashtech.ecommercialwebsite.services.RatingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final ModelMapper mapper;

    @Override
    public RatingResponse getUserRatingByProduct(int userId, int productId) {
       return new RatingResponse(
               ratingRepository.getRatingPointsFromProduct(productId),
               ratingRepository.getUserRatingPointsByProduct(userId, productId)
       );
    }

    @Override
    public UserRatingResponse rateProduct(UserRatingRequest userRatingRequest) {

        Product product = productRepository.findById(userRatingRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Product not found with id: %s", userRatingRequest.getProductId())));

        Account account = userRepository.findById((long) userRatingRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("User not found with id: %s", userRatingRequest.getUserId())));

        //retrieve rating information by userId and productId
        RatingResponse ratingResponse = getUserRatingByProduct(
                userRatingRequest.getUserId(), userRatingRequest.getProductId()
        );

        //user's never rated this product before -> create new rating
        if(ratingResponse.getUserRatingPoints() == null) {
            log.info(String.format("FIRST TIME RATING PRODUCT OF USER ID: %s", userRatingRequest.getUserId()));
            Rating rating = Rating.builder()
                    .ratingPoints(userRatingRequest.getRatingPoints())
                    .product(product)
                    .account(account)
                    .build();
            ratingRepository.save(rating);
            return mapper.map(rating, UserRatingResponse.class);
        }
        //user's rated this product -> update rating
        else {
            Rating oldRating = ratingRepository.findRatingByAccountAndProduct(account, product);
            //update rating points
            oldRating.setRatingPoints(userRatingRequest.getRatingPoints());
            //save new rating points
            ratingRepository.save(oldRating);
            log.info("UPDATE RATING POINTS");
            return mapper.map(oldRating, UserRatingResponse.class);
        }

    }


}
