package com.nashtech.ecommercialwebsite.controller.user;

import com.nashtech.ecommercialwebsite.dto.request.CartUpdateRequest;
import com.nashtech.ecommercialwebsite.dto.response.CartItemDto;
import com.nashtech.ecommercialwebsite.dto.response.CartResponse;
import com.nashtech.ecommercialwebsite.services.CartItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@AllArgsConstructor
@RequestMapping("/customer/api/cart")
public class ShoppingCartController {

    private final CartItemService cartItemService;


    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public CartResponse getAllItemsByCart(HttpServletRequest request) {
        return cartItemService.getAllCartItemsOfUser(request);
    }

    @PostMapping("/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CartItemDto addItemToCart(@PathVariable("productId") int productId, HttpServletRequest request) {
        return cartItemService.addProductToCart(productId, request);
    }

    @PutMapping()
    public CartResponse updateCart(@RequestBody CartUpdateRequest cartUpdateRequest,
                                   HttpServletRequest request) {
        return cartItemService.updateCartItems(cartUpdateRequest, request);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public CartItemDto removeProductFromShoppingCart(@PathVariable("productId") int productId,
                                                     HttpServletRequest request) {
        return cartItemService.removeProductFromCart(productId, request);
    }

}
