package com.nashtech.ecommercialwebsite.services;

import com.nashtech.ecommercialwebsite.data.entity.Account;
import com.nashtech.ecommercialwebsite.dto.response.UserAccountDto;
import com.nashtech.ecommercialwebsite.dto.response.UserAccountResponse;

public interface UserService {
     String signUpUser(Account userAccount);

     int enableUser(String email);

     UserAccountResponse getAllUserAccounts(int pageNo, int pageSize, String sortBy, String sortDir);

     UserAccountDto getAccountById(long id);

     UserAccountDto changeUserAccountStatus(UserAccountDto accountDto);

     void createShoppingCart(String username);

}
