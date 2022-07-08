package com.nashtech.ecommercialwebsite.dto.request;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Builder
@AllArgsConstructor
@Getter @Setter
@NoArgsConstructor
public class UserRatingRequest {

    @Min(value = 1, message = "Rating points must at least 1 ")
    @Max(value = 5, message = "Rating points max is 5")
    private int ratingPoints;

    @Min(value = 1, message = "Product ID must be positive")
    private int productId;

}
