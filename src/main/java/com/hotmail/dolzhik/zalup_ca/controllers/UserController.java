package com.hotmail.dolzhik.zalup_ca.controllers;

import com.hotmail.dolzhik.zalup_ca.dto.request.RegisterRequestDto;
import com.hotmail.dolzhik.zalup_ca.dto.ZalupcaResponse;
import com.hotmail.dolzhik.zalup_ca.entities.User;
import com.hotmail.dolzhik.zalup_ca.services.ICaptchaService;
import com.hotmail.dolzhik.zalup_ca.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.sql.Date;

@RestController
public class UserController {

    private final UserService userService;
    private final ICaptchaService captchaService;

    @Autowired
    public UserController(UserService userService, ICaptchaService captchaService) {
        this.userService = userService;
        this.captchaService = captchaService;
    }

    @PostMapping(value = "/register")
    ResponseEntity register(@Valid @RequestBody RegisterRequestDto registerRequestDto) {

        if(captchaService.isValid(registerRequestDto.getToken(),"register")) {
            User user = userService.findByLogin(registerRequestDto.getLogin());
            if (user == null) {
                user = new User();
                user.setLogin(registerRequestDto.getLogin());
                user.setPassword(registerRequestDto.getPassword());
                user.setPoints(100);
                user.setCreated(new Date(new java.util.Date().getTime()));
                userService.register(user);
                return new ResponseEntity<>(new ZalupcaResponse("User successfully registered."), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ZalupcaResponse("User cannot be registered."), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new ZalupcaResponse("Captcha is invalid."),HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/admin/users")
    ResponseEntity getUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/user")
    ResponseEntity getCurrentUser(Principal principal) {
        return new ResponseEntity<>(userService.findByLogin(principal.getName()), HttpStatus.OK);
    }

}
