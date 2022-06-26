package com.nashtech.ecommercialwebsite.services;

import com.nashtech.ecommercialwebsite.data.entity.Account;

public interface UserService {
    public String signUpUser(Account userAccount);
    public int enableUser(String email);
}
