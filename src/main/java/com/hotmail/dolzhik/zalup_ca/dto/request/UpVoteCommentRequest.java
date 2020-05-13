package com.hotmail.dolzhik.zalup_ca.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = false)
@Data
public class UpVoteCommentRequest extends CaptchaToken {
    @NotNull(message = "CommentId cannot be empty.")
    private int commentId;
}
