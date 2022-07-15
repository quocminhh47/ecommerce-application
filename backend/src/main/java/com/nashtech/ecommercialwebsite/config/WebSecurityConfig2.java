package com.nashtech.ecommercialwebsite.config;

import com.nashtech.ecommercialwebsite.security.jwt.JwtAuthoizationTokenFilter;
import com.nashtech.ecommercialwebsite.security.jwt.JwtUtils;
import com.nashtech.ecommercialwebsite.services.impl.UserDetailServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.nashtech.ecommercialwebsite.utils.AppConstants.AUTH_WHITELIST;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class WebSecurityConfig2 extends WebSecurityConfigurerAdapter {

    private final UserDetailServiceImpl userDetailService;

   // private final JwtAuthEntryPoint unauthorizedHandler;

    private final JwtUtils jwtUtils;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Bean
    public JwtAuthoizationTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthoizationTokenFilter(jwtUtils, userDetailService);
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        // TODO
        authenticationManagerBuilder.userDetailsService(userDetailService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
               // .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
               // .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().antMatchers(
                "/api/auth/login/**",
                "/token/refresh/**",
                "/v1/api/registration/**",
                "/api/auth/login").permitAll();
        http.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll();
        http.authorizeRequests().antMatchers("/admin/api/**").hasRole("ADMIN");
//        http.authorizeRequests().antMatchers("/admin/api/**").permitAll();//.hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/customer/api/**").hasAnyRole("USER", "ADMIN");
        http.authorizeRequests().antMatchers("/user/api/**").permitAll();

        http.authorizeRequests().anyRequest().permitAll();
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
