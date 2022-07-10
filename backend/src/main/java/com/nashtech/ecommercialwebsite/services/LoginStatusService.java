package com.nashtech.ecommercialwebsite.services;

import com.nashtech.ecommercialwebsite.dto.response.LoginStatusResponse;

import javax.servlet.http.HttpServletRequest;

public interface LoginStatusService {

    LoginStatusResponse getLoginStatus(HttpServletRequest request);
}
