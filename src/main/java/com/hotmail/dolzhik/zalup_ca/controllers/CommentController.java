package com.hotmail.dolzhik.zalup_ca.controllers;

import com.hotmail.dolzhik.zalup_ca.dto.CommentDto;
import com.hotmail.dolzhik.zalup_ca.dto.ZalupcaResponse;
import com.hotmail.dolzhik.zalup_ca.dto.request.UpVoteCommentRequest;
import com.hotmail.dolzhik.zalup_ca.entities.Comment;
import com.hotmail.dolzhik.zalup_ca.entities.CommentUpVote;
import com.hotmail.dolzhik.zalup_ca.entities.Post;
import com.hotmail.dolzhik.zalup_ca.entities.User;
import com.hotmail.dolzhik.zalup_ca.services.*;
import com.hotmail.dolzhik.zalup_ca.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.Date;

@RestController
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;
    private final VoteService voteService;
    private final ICaptchaService captchaService;

    @Autowired
    public CommentController(CommentService commentService, PostService postService, UserService userService, VoteService voteService, ICaptchaService captchaService) {
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
        this.voteService = voteService;
        this.captchaService = captchaService;
    }

    @PostMapping(value = "/addComment")
    ResponseEntity addComment(@Valid @RequestBody CommentDto commentDto, Principal principal) {
        if (captchaService.isValid(commentDto.getToken(), "addComment")) {
            User user = userService.findByLogin(principal.getName());
            Post post = postService.findPostById(commentDto.getPostId());
            if (post != null) {
                Comment comment = new Comment();
                comment.setText(commentDto.getText());
                comment.setCreated(new Timestamp(new Date().getTime()));
                comment.setUser(user);
                comment.setPost(post);
                commentService.addComment(comment);
                return new ResponseEntity<>(new ZalupcaResponse("Comment has been added."), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ZalupcaResponse("Comment has not been added."), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ZalupcaResponse("Captcha is invalid."), HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/upVote")
    ResponseEntity addUpVote(@RequestBody @Valid UpVoteCommentRequest request, Principal principal) {
        if (captchaService.isValid(request.getToken(), "upVote")) {
            User user = userService.findByLogin(principal.getName());
            Comment comment = commentService.getCommentById(request.getCommentId());
            if (comment != null
                    && !user.getId().equals(comment.getUser().getId())
                    && !voteService.isCommentUpVotedByUser(user.getId(), comment.getId())) {
                CommentUpVote vote = new CommentUpVote();
                vote.setUser(user);
                vote.setComment(comment);
                voteService.addVote(vote);
                userService.changePoints(comment.getUser(), Constants.UP_VOTE_COST);
                return new ResponseEntity<>(new ZalupcaResponse("UpVote added."), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ZalupcaResponse("UpVote cannot be added."), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ZalupcaResponse("Captcha is invalid."), HttpStatus.BAD_REQUEST);
    }
}
