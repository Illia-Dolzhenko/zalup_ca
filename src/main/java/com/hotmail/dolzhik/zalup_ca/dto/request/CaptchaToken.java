package com.hotmail.dolzhik.zalup_ca.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CaptchaToken {
    @NotNull(message = "Token must not be empty.")
    private String token;
}
