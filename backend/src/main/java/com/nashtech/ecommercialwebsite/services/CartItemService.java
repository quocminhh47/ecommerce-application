package com.nashtech.ecommercialwebsite.services;

import com.nashtech.ecommercialwebsite.dto.request.CartUpdateRequest;
import com.nashtech.ecommercialwebsite.dto.response.CartItemDto;
import com.nashtech.ecommercialwebsite.dto.response.CartResponse;

import javax.servlet.http.HttpServletRequest;

public interface CartItemService {

    CartResponse getAllCartItemsOfUser(HttpServletRequest request);

    CartItemDto addProductToCart(int productId, HttpServletRequest request);

    CartResponse updateCartItems(CartUpdateRequest cartUpdateRequest, HttpServletRequest request);

}
