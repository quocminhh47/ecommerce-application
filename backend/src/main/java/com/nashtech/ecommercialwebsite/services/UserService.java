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
                                            String sortDirection,
                                            HttpServletRequest request);

     UserAccountDto getAccountById(int id,HttpServletRequest request);

     UserAccountDto changeUserAccountStatus(UserRequest userRequest, int userId,HttpServletRequest request);

     void createShoppingCart(String username);

}
