package com.nashtech.ecommercialwebsite.services;


import com.nashtech.ecommercialwebsite.data.entity.Cart;

public interface CartService {

    Cart findCartByUsername(String  username);


}
