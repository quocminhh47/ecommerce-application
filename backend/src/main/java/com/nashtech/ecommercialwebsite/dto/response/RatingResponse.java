package com.nashtech.ecommercialwebsite.dto.response;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingResponse {

    private Double productRatingPoints;

    private Integer userRatingPoints;

}
