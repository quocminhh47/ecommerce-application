package com.nashtech.ecommercialwebsite.repository;


import com.nashtech.ecommercialwebsite.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ConfirmationTokenRepository
        extends JpaRepository<ConfirmationToken, Long> {

}
