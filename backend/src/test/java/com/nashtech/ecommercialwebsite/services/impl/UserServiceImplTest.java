package com.nashtech.ecommercialwebsite.services.impl;

import com.nashtech.ecommercialwebsite.data.entity.Account;
import com.nashtech.ecommercialwebsite.data.entity.Cart;
import com.nashtech.ecommercialwebsite.data.repository.RoleRepository;
import com.nashtech.ecommercialwebsite.data.repository.UserRepository;
import com.nashtech.ecommercialwebsite.dto.request.UserRequest;
import com.nashtech.ecommercialwebsite.dto.response.LoginStatusResponse;
import com.nashtech.ecommercialwebsite.dto.response.UserAccountDto;
import com.nashtech.ecommercialwebsite.services.ConfirmationTokenService;
import com.nashtech.ecommercialwebsite.services.LoginStatusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    ConfirmationTokenService confirmationTokenService;
    LoginStatusService loginStatusService;
    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    RoleRepository roleRepository;
    ModelMapper mapper;
    UserServiceImpl userServiceImpl;
    Account userAccount;
    HttpServletRequest request;
    UserAccountDto expectedUserDto;
    UserRequest userRequest;
    LoginStatusResponse loginStatusResponse;

    @BeforeEach
    void setUp() {
//        confirmationTokenService = mock(ConfirmationTokenService.class);
//        loginStatusService = mock(LoginStatusService.class);
//        userRepository = mock(UserRepository.class);
//        bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);
//        roleRepository = mock(RoleRepository.class);
//        mapper = mock(ModelMapper.class);
//        userServiceImpl = new UserServiceImpl(confirmationTokenService, loginStatusService,
//                userRepository, bCryptPasswordEncoder, roleRepository,
//                mapper);
//        userAccount = mock(Account.class);
//        request = mock(HttpServletRequest.class);
//        expectedUserDto = mock(UserAccountDto.class);
//        userRequest = new UserRequest(true, false);
//        loginStatusResponse = mock(LoginStatusResponse.class);
    }


    @Test
    void givenAccountId_whenGetAccountById_thenReturnUserAccountDtoObject() {
        //given
//        when(userRepository.findById(1)).thenReturn(Optional.of(userAccount));
//        when(mapper.map(userAccount, UserAccountDto.class)).thenReturn(expectedUserDto);
//
//        //when
//        UserAccountDto actualAccountDto = userServiceImpl.getAccountById(1, request);
//
//        //then
//        assertThat(actualAccountDto).isEqualTo(expectedUserDto);

    }

    @Test
    void givenUserRequest_whenChangeUserStatus_thenReturnUserAccountDto() {
        //given
//        when(userRepository.findById(1)).thenReturn(Optional.of(userAccount));
//        Account updatedAccount = mock(Account.class);
//        when(userRepository.save(userAccount)).thenReturn(updatedAccount);
//
//        UserAccountDto expectedAccountDto = mock(UserAccountDto.class);
//        when(mapper.map(updatedAccount, UserAccountDto.class)).thenReturn(expectedAccountDto);
//        when(loginStatusService.getLoginStatus(request)).thenReturn(loginStatusResponse);
//        //when
//        UserAccountDto actualAccountDto = userServiceImpl.changeUserAccountStatus(userRequest, 1, request);
//
//        //then
//        verify(userAccount).setEnabled(userRequest.getIsEnabled());
//        verify(userAccount).setIsNonLocked(userRequest.getIsNonLocked());
//        verify(expectedAccountDto).setLoginStatus(loginStatusResponse);
//        assertThat(actualAccountDto).isEqualTo(expectedAccountDto);

    }

}