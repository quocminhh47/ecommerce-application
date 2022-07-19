package com.nashtech.ecommercialwebsite.services;

import com.nashtech.ecommercialwebsite.data.entity.Account;
import com.nashtech.ecommercialwebsite.dto.request.UserRequest;
import com.nashtech.ecommercialwebsite.dto.response.UserAccountDto;
import com.nashtech.ecommercialwebsite.dto.response.UserAccountResponse;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

     String signUpUser(Account userAccount);

     UserAccountResponse getAllUserAccounts(int pageNo,
                                            int pageSize,
                                            String sortBy,
                                            String sortDirection);

     UserAccountDto getAccountById(int id);

     UserAccountDto changeUserAccountStatus(UserRequest userRequest, int userId);

     void createShoppingCart(String username);

}
