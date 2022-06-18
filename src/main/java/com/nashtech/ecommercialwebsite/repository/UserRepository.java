package com.nashtech.ecommercialwebsite.repository;

import com.nashtech.ecommercialwebsite.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Account, String> {
    Optional<Account> findAccountByUsername(String email);
    @Transactional
    @Modifying
    @Query("UPDATE Account a " +
            "SET a.enabled = TRUE WHERE a.username = ?1") //username = email
    int enableUser(String email);

}
