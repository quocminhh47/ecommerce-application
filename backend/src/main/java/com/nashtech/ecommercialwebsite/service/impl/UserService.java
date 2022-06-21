package com.nashtech.ecommercialwebsite.service.impl;

import com.nashtech.ecommercialwebsite.data.entity.Account;
import com.nashtech.ecommercialwebsite.data.entity.ConfirmationToken;
import com.nashtech.ecommercialwebsite.data.repository.UserRepository;
import com.nashtech.ecommercialwebsite.service.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final static String USER_NOT_FOUND_MSG =
            "user with email %s not found";
    private final ConfirmationTokenService confirmationTokenService;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username)  throws UsernameNotFoundException {
        Optional<Account> user = userRepository.findAccountByUsername(username);
        if(user.isPresent()){
            return new org.springframework.security.core.userdetails.User(
                    user.get().getUsername(),
                    user.get().getPassword(),
                    getGrantedAuthorities(user.get())
            );
        }
        else throw  new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username));

    }

    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }

    //Handle when user register - and this give token
    public String signUpUser(Account userAccount){
        boolean isUserExist = userRepository.
                findAccountByUsername(userAccount.getUsername())
                .isPresent();
        if(isUserExist){
            //TODO check if account is not confirmed then resend email to confirm
            throw new IllegalStateException("email already taken");
        }
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
        System.out.println(confirmationToken.toString());
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
}
