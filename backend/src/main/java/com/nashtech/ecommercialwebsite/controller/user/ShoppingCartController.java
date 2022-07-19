package com.nashtech.ecommercialwebsite.controller.user;

import com.nashtech.ecommercialwebsite.dto.request.CartUpdateRequest;
import com.nashtech.ecommercialwebsite.dto.response.CartItemDto;
import com.nashtech.ecommercialwebsite.dto.response.CartResponse;
import com.nashtech.ecommercialwebsite.services.CartItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Shopping feature",
        description = "Include add products to cart, change cart item, order")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@AllArgsConstructor
@RequestMapping("/customer/api/cart")
public class ShoppingCartController {

    private final CartItemService cartItemService;


    @GetMapping()
    @Operation(summary = "Get cart detail", description = "Provide all item in shopping cart, include product and price")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Successfully retrieved"),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request - The request is invalid",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "404",
                    description = "Not found - The url was not found",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden - The request is not permitted",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "500",
                    description = "Internal Error - There were some error while processing in server",
                    content = {@Content(examples = {@ExampleObject()})})
    })
    @ResponseStatus(HttpStatus.OK)
    public CartResponse getAllItemsByCart() {
        return cartItemService.getAllCartItemsOfUser();
    }

    @PostMapping("/{productId}")
    @Operation(summary = "Add item to cart", description = "Provide ability of adding a product to customer's shopping cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED - Successfully created"),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request - The request is invalid",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "404",
                    description = "Not found - The url was not found",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden - The request is not permitted",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "500",
                    description = "Internal Error - There were some error while processing in server",
                    content = {@Content(examples = {@ExampleObject()})})
    })
    @ResponseStatus(HttpStatus.CREATED)
    public CartItemDto addItemToCart(@PathVariable("productId") int productId) {
        return cartItemService.addProductToCart(productId);
    }

    @PutMapping()
    @Operation(summary = "Change cart item information", description = "Change the quantity of item in shopping cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Successfully changed"),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request - The request is invalid",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "404",
                    description = "Not found - The url was not found",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden - The request is not permitted",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "500",
                    description = "Internal Error - There were some error while processing in server",
                    content = {@Content(examples = {@ExampleObject()})})
    })
    @ResponseStatus(HttpStatus.OK)
    public CartResponse updateCart(@RequestBody CartUpdateRequest cartUpdateRequest) {
        return cartItemService.updateCartItems(cartUpdateRequest);
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "Delete item in shopping cart", description = "Remove an item from customer's shopping cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED - Successfully created"),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request - The request is invalid",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "404",
                    description = "Not found - The url was not found",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden - The request is not permitted",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "500",
                    description = "Internal Error - There were some error while processing in server",
                    content = {@Content(examples = {@ExampleObject()})})
    })
    @ResponseStatus(HttpStatus.OK)
    public CartItemDto removeProductFromShoppingCart(@PathVariable("productId") int productId) {
        return cartItemService.removeProductFromCart(productId);
    }

}
