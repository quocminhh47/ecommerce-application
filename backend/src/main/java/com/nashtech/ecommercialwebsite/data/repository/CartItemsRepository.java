package com.nashtech.ecommercialwebsite.data.repository;

import com.nashtech.ecommercialwebsite.data.entity.Cart;
import com.nashtech.ecommercialwebsite.data.entity.CartDetail;
import com.nashtech.ecommercialwebsite.data.entity.CartDetailId;
import com.nashtech.ecommercialwebsite.data.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CartItemsRepository extends JpaRepository<CartDetail, CartDetailId> {

    List<CartDetail> findAllByCart_Id(int cartId);

    Optional<CartDetail> findCartDetailsByProductAndCart(Product product, Cart cart);


    @Transactional
    @Modifying
    @Query(value = "delete from cart_detail  " +
            "where product_id = ?1 and cart_id = ?2", nativeQuery = true)
    int deleteFromCartDetailByID(int productId, int cartId);
}
