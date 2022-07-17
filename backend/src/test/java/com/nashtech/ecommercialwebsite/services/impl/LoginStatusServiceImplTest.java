package com.nashtech.ecommercialwebsite.services.impl;

import com.nashtech.ecommercialwebsite.data.entity.Account;
import com.nashtech.ecommercialwebsite.data.entity.Role;
import com.nashtech.ecommercialwebsite.data.repository.UserRepository;
import com.nashtech.ecommercialwebsite.dto.response.LoginStatusResponse;
import com.nashtech.ecommercialwebsite.exceptions.ResourceNotFoundException;
import com.nashtech.ecommercialwebsite.security.jwt.JwtUtils;
import com.nashtech.ecommercialwebsite.services.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.http.HttpServletRequest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginStatusServiceImplTest {
    JwtService jwtService;
    JwtUtils jwtUtils;
    UserRepository userRepository;
    LoginStatusServiceImpl loginStatusServiceImpl;
    HttpServletRequest request;
    Account account;
    @BeforeEach
    void setUp() {
        jwtService = mock(JwtService.class);
        jwtUtils = mock(JwtUtils.class);
        userRepository = mock(UserRepository.class);
        loginStatusServiceImpl = new LoginStatusServiceImpl(jwtService, jwtUtils, userRepository);

        request = mock(HttpServletRequest.class);
        account = mock(Account.class);
    }

    //positive case
    @Test
    void givenHttpServletRequestWithValidToken_whenGetLoginStatus_thenReturnLoginStatusResponse() {
        //given
        when(jwtService.parseJwt(request)).thenReturn("Bearertoken");
        when(jwtUtils.validateJwtToken("Bearertoken")).thenReturn(true);
        when(jwtService.getUsernameFromToken("Bearertoken")).thenReturn("username");
        when(userRepository.findAccountByUsername("username")).thenReturn(Optional.of(account));
        when(account.getFirstName()).thenReturn("Pham");
        when(account.getLastName()).thenReturn("Quoc");
        Role role = mock(Role.class);
        when(account.getRole()).thenReturn(role);
        when(role.getRoleName()).thenReturn("admin");
        //when
        LoginStatusResponse actualResponse = loginStatusServiceImpl.getLoginStatus(request);

        //then
        assertThat(actualResponse.getFullname()).isEqualTo("Pham Quoc");
        assertThat(actualResponse.getRoleName()).isEqualTo("admin");

    }

    //negative case
    @Test
    void givenHttpServletRequestWithNullToken_whenGetLoginStatus_thenReturnNullLoginStatusResponse() {
        //given
        when(jwtService.parseJwt(request)).thenReturn("Bearertoken");
        when(jwtUtils.validateJwtToken("Bearertoken")).thenReturn(false);

        //when
        LoginStatusResponse actualResponse = loginStatusServiceImpl.getLoginStatus(request);

        //then
        assertThat(actualResponse.getFullname()).isEqualTo(null);
        assertThat(actualResponse.getRoleName()).isEqualTo(null);

    }

    //negative case
    @Test
    void givenHttpServletRequestWithInValidToken_whenGetLoginStatus_thenThrowsException() {
        //given
        when(jwtService.parseJwt(request)).thenReturn("Bearertoken");
        when(jwtUtils.validateJwtToken("Bearertoken")).thenReturn(true);
        when(jwtService.getUsernameFromToken("Bearertoken")).thenReturn("username");
        when(userRepository.findAccountByUsername("username")).thenReturn(Optional.empty());

        //when
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> loginStatusServiceImpl.getLoginStatus(request));

        //then
        assertThat(exception.getMessage()).isEqualTo(String.format("Username %s not found ", "username"));

    }
}