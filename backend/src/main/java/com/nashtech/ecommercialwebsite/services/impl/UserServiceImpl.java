package com.nashtech.ecommercialwebsite.services.impl;

import com.nashtech.ecommercialwebsite.data.entity.Account;
import com.nashtech.ecommercialwebsite.data.entity.ConfirmationToken;
import com.nashtech.ecommercialwebsite.data.entity.Role;
import com.nashtech.ecommercialwebsite.data.repository.RoleRepository;
import com.nashtech.ecommercialwebsite.data.repository.UserRepository;
import com.nashtech.ecommercialwebsite.dto.response.UserAccountDto;
import com.nashtech.ecommercialwebsite.dto.response.UserAccountResponse;
import com.nashtech.ecommercialwebsite.exceptions.ResourceNotFoundException;
import com.nashtech.ecommercialwebsite.services.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements com.nashtech.ecommercialwebsite.services.UserService, UserDetailsService{
    private static final String USER_NOT_FOUND_MSG = "User with email %s not found";
    private static final String USER_ROLE_NOT_FOUND_MSG = "Role %s not found in the database";
    private static final String USER_ROLE_NAME = "USER";
    private final ConfirmationTokenService confirmationTokenService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;
    private final ModelMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username)  throws UsernameNotFoundException {
        Account user  = userRepository.findAccountByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                getGrantedAuthorities(user)
        );
    }

    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }



    //Handle when user register - and this give token
    public String signUpUser(Account userAccount){
        //encoded password
        String encodedPassword = bCryptPasswordEncoder.encode(userAccount.getPassword());
        userAccount.setPassword(encodedPassword);
        //save user to DB
        userRepository.save(userAccount);
        //generate confirmation token
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                userAccount
        );
        //save confirmation token
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }



    private Collection<GrantedAuthority> getGrantedAuthorities (Account account){
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        if(account.getRole().getRoleName().equalsIgnoreCase("admin"))
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        if(account.getRole().getRoleName().equalsIgnoreCase("user"))
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return authorities;
    }
    @Override
    public UserAccountResponse getAllUserAccounts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        //create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        // get role where name = "USER"
        Role role = roleRepository.findRolesByRoleName(USER_ROLE_NAME)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                String.format(USER_ROLE_NOT_FOUND_MSG,
                                        USER_ROLE_NAME)));
        Page<Account> accounts = userRepository.findAllByRole(pageable, role);
        return getContent(accounts);
    }


    private UserAccountDto mapToDto(Account account) {
        return mapper.map(account, UserAccountDto.class);
    }

    @Override
    public UserAccountDto getAccountById(long id) {
        Account account = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("User with ID: %s not found", id)
                ));
        return mapper.map(account, UserAccountDto.class);
    }

    @Transactional
    @Override
    public UserAccountDto changeUserAccountStatus(UserAccountDto accountDto) {
        //TODO: Check others property ( name, password, id,..) has changed or not
        userRepository.changeUserAccountStatus(
                accountDto.getId(),
                accountDto.getEnabled(),
                accountDto.getLocked()
        );
        Account updatedAccount = userRepository.findById(accountDto.getId())
                .orElseThrow( () -> new ResourceNotFoundException(
                        String.format("User account with ID: %s not found", accountDto.getId())
                ));
        return mapper.map(updatedAccount, UserAccountDto.class);
    }


    private UserAccountResponse getContent(Page<Account> accounts) {
        List<Account> listOfAccounts = accounts.getContent();
        List<UserAccountDto> content = listOfAccounts.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return UserAccountResponse.builder()
                .userAccountContent(content)
                .pageNo(accounts.getNumber())
                .pageSize(accounts.getSize())
                .totalElements(accounts.getTotalElements())
                .totalPages(accounts.getTotalPages())
                .last(accounts.isLast())
                .build();

    }
}
