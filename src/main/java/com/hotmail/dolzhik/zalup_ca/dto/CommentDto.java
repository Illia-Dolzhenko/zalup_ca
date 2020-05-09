package com.hotmail.dolzhik.zalup_ca.dto;

import com.hotmail.dolzhik.zalup_ca.dto.request.CaptchaToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = false)
@Data
public class CommentDto extends CaptchaToken {
    @NotNull(message = "Post id cannot be empty.")
    private Integer postId;
    @NotNull(message = "Text cannot be empty.")
    @Size(min = 8, max = 256, message = "Text must not be bigger than 256 characters.")
    private String text;
}
