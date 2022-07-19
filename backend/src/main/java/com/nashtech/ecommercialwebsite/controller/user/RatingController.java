package com.nashtech.ecommercialwebsite.controller.user;

import com.nashtech.ecommercialwebsite.dto.request.UserRatingRequest;
import com.nashtech.ecommercialwebsite.dto.response.RatingResponse;
import com.nashtech.ecommercialwebsite.dto.response.UserRatingResponse;
import com.nashtech.ecommercialwebsite.services.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Tag(name = "Rating Resources",
        description = "Rating resources that provide create / access to the rating information of product")
@RestController
@RequestMapping("/customer/api/ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

   @GetMapping("/ratings")
    @Operation(summary = "Get rating of product", description = "Provides the rating information of user about product")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "OK - Successfully retrieved"),
            @ApiResponse( responseCode = "400",
                    description = "Bad Request - The request is invalid",
                    content = {@Content(examples = {@ExampleObject})}),
            @ApiResponse( responseCode = "404",
                    description = "Not found - The resource was not found",
                    content = {@Content(examples = {@ExampleObject})}),
            @ApiResponse(responseCode = "500",
                    description = "Internal Error - There were some error while processing in server",
                    content = {@Content(examples = {@ExampleObject()})})
    })
    public ResponseEntity<RatingResponse> getRatingInfo(@RequestParam("product-id") int productId) {
        return new ResponseEntity<>(ratingService.getUserRatingByProduct( productId), HttpStatus.OK);
    }

    @PostMapping()
    @Operation(summary = "Rate the products", description = "Customers can rate the product by scores from 1-5 by here")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "201", description = "OK - Successfully rating"),
            @ApiResponse( responseCode = "400",
                    description = "Bad Request - The rating is invalid",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse( responseCode = "401",
                    description = "Unauthorized -  Authorization information is missing or invalid"),
            @ApiResponse( responseCode = "403",
                    description = "Forbidden -  The request is not permitted"),
            @ApiResponse( responseCode = "404",
                    description = "Not found - The request resources was not found",
                    content = {@Content(examples = {@ExampleObject})}),
            @ApiResponse(responseCode = "500",
                    description = "Internal Error - There were some error while processing in server",
                    content = {@Content(examples = {@ExampleObject()})})
    })
    public ResponseEntity<UserRatingResponse> rateProduct( @Valid @RequestBody UserRatingRequest userRatingRequest){
        return new ResponseEntity<>(ratingService.rateProduct(userRatingRequest), HttpStatus.CREATED);
    }
}
