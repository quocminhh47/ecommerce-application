package com.nashtech.ecommercialwebsite.repository;

import com.nashtech.ecommercialwebsite.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Account, String> {


}
