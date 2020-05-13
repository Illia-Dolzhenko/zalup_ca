package com.hotmail.dolzhik.zalup_ca.dto.request;

import com.hotmail.dolzhik.zalup_ca.dto.request.CaptchaToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = false)
@Data
public class RatingDto extends CaptchaToken {
    @NotNull(message = "Category cannot be empty.")
    private String category;
    @NotNull(message = "Score cannot be empty.")
    @Min(value = 1,message = "Score cannot be less then 1.")
    @Max(value = 10,message = "Score cannot be bigger than 10.")
    private Integer score;
    @NotNull(message = "Post id cannot be empty.")
    private Integer postId;
}
