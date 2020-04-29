package com.hotmail.dolzhik.zalup_ca.security;

import com.hotmail.dolzhik.zalup_ca.entities.User;
import com.hotmail.dolzhik.zalup_ca.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ZalupcaAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ZalupcaAuthenticationProvider(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String password = authentication.getCredentials().toString();

        //System.out.println("Login :" + login + "\nPassword: " + password);

        User user = userService.findByLogin(login);

        if(user!=null && passwordEncoder.matches(password,user.getPassword())){

            List<GrantedAuthority> authorities = new ArrayList<>();

            user.getRoles().forEach(role -> {
                authorities.add((new SimpleGrantedAuthority(role.getName())));
            });
            Authentication auth = new UsernamePasswordAuthenticationToken(login, password, authorities);
            return auth;
        }else {
            System.out.println(passwordEncoder.matches(password,user.getPassword()));
            throw new BadCredentialsException("Wrong login or password.");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
