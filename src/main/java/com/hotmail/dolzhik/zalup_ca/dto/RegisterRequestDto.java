package com.hotmail.dolzhik.zalup_ca.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegisterRequestDto {
    @NotNull(message = "Login cannot be empty.")
    @Size(min = 5, message = "Login must not be less then 5 characters.")
    private String login;
    @NotNull(message = "Password cannot be empty.")
    @Size(min = 10,message = "Password should be at least 10 characters long.")
    private String password;
}
