package com.hotmail.dolzhik.zalup_ca.dto;

import com.hotmail.dolzhik.zalup_ca.dto.request.CaptchaToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = false)
@Data
public class CreatePostDto extends CaptchaToken {
    @NotNull(message = "Image link cannot be empty.")
    private String image;
    @NotNull(message = "Destruction time cannot be empty.")
    @Digits(integer=2, fraction=0, message = "Destruction time cannot be bigger than 99.")
    private Integer timeToLive;
    @NotNull(message = "Text cannot be empty.")
    @Size(min = 16, max = 256, message = "Text must not be bigger than 256 characters.")
    private String text;
}
