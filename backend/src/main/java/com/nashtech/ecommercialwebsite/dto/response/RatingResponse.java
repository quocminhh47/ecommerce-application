package com.nashtech.ecommercialwebsite.dto.response;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
public class RatingResponse {
    private Double productRatingPoints;
    private Integer userRatingPoints;

    public RatingResponse(Double productRatingPoints, Integer userRatingPoints) {
        this.productRatingPoints = productRatingPoints;
        this.userRatingPoints = userRatingPoints;
    }
}
