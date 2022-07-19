package com.nashtech.ecommercialwebsite.services;

import com.nashtech.ecommercialwebsite.dto.request.CartUpdateRequest;
import com.nashtech.ecommercialwebsite.dto.response.CartItemDto;
import com.nashtech.ecommercialwebsite.dto.response.CartResponse;

import javax.servlet.http.HttpServletRequest;

public interface CartItemService {

    CartResponse getAllCartItemsOfUser();

    CartItemDto addProductToCart(int productId);

    CartItemDto removeProductFromCart(int productId);

    CartResponse updateCartItems(CartUpdateRequest cartUpdateRequest);

}
