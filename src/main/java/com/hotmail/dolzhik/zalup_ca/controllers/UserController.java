package com.hotmail.dolzhik.zalup_ca.controllers;

import com.hotmail.dolzhik.zalup_ca.dto.RegisterRequestDto;
import com.hotmail.dolzhik.zalup_ca.dto.ZalupcaResponse;
import com.hotmail.dolzhik.zalup_ca.entities.Role;
import com.hotmail.dolzhik.zalup_ca.entities.User;
import com.hotmail.dolzhik.zalup_ca.repositories.RoleRepository;
import com.hotmail.dolzhik.zalup_ca.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private  final UserService userService;

    @Autowired
    public UserController(UserService userService) {
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
            return new ResponseEntity<>(new ZalupcaResponse("User successfully registered."),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ZalupcaResponse("User cannot be registered."),HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/admin/users")
    ResponseEntity getUsers(){
        return new ResponseEntity<>(userService.findAll(),HttpStatus.OK);
    }

    @GetMapping(value = "/user")
    ResponseEntity getCurrentUser(Principal principal){
        return new ResponseEntity<>(userService.findByLogin(principal.getName()),HttpStatus.OK);
    }

}
