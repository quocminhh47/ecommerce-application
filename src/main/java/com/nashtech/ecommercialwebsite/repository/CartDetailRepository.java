package com.nashtech.ecommercialwebsite.repository;

import com.nashtech.ecommercialwebsite.entity.CartDetail;
import com.nashtech.ecommercialwebsite.entity.CartDetail_ID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartDetailRepository extends JpaRepository<CartDetail, CartDetail_ID> {

}
