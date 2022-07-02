package com.nashtech.ecommercialwebsite.services.impl;

import com.nashtech.ecommercialwebsite.data.entity.Account;
import com.nashtech.ecommercialwebsite.data.entity.Cart;
import com.nashtech.ecommercialwebsite.data.repository.CartItemsRepository;
import com.nashtech.ecommercialwebsite.data.repository.CartRepository;
import com.nashtech.ecommercialwebsite.data.repository.UserRepository;
import com.nashtech.ecommercialwebsite.dto.request.CartUpdateRequest;
import com.nashtech.ecommercialwebsite.dto.response.CartResponse;
import com.nashtech.ecommercialwebsite.exceptions.ResourceNotFoundException;
import com.nashtech.ecommercialwebsite.services.CartItemService;
import com.nashtech.ecommercialwebsite.services.CartService;
import com.nashtech.ecommercialwebsite.services.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private static final String USER_NOT_FOUND_MSG = "User with email %s not found";

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final CartRepository cartRepository;
/*


    private final CartItemsRepository cartItemsRepository;

    private final CartService cartService;

    private final CartItemService cartItemService;*/

    @Override
    public Cart findCartByUsername(String username) {
        Account account  = userRepository.findAccountByUsername(username)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
        Cart cart = account.getCart();
        if(cart != null) return cart;
        else throw new ResourceNotFoundException("Cart not found");

    }

}
