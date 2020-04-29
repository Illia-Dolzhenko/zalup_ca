package com.hotmail.dolzhik.zalup_ca.controllers;

import com.hotmail.dolzhik.zalup_ca.dto.RegisterRequestDto;
import com.hotmail.dolzhik.zalup_ca.entities.Role;
import com.hotmail.dolzhik.zalup_ca.entities.User;
import com.hotmail.dolzhik.zalup_ca.repositories.RoleRepository;
import com.hotmail.dolzhik.zalup_ca.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RegisterController {

    private  final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register")
    ResponseEntity register(@Valid @RequestBody RegisterRequestDto registerRequestDto){
        User user = userService.findByLogin(registerRequestDto.getLogin());

        if(user == null){
            user = new User();
            user.setLogin(registerRequestDto.getLogin());
            user.setPassword(registerRequestDto.getPassword());
            user.setPoints(100);
            user.setCreated(new Date(new java.util.Date().getTime()));
            userService.register(user);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
    }

}
