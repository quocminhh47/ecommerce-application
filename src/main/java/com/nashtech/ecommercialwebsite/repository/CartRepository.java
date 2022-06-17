package com.nashtech.ecommercialwebsite.repository;

import com.nashtech.ecommercialwebsite.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {

}
