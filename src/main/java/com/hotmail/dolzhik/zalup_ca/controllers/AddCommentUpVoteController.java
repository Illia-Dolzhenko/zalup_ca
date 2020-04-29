package com.hotmail.dolzhik.zalup_ca.controllers;

import com.hotmail.dolzhik.zalup_ca.entities.Comment;
import com.hotmail.dolzhik.zalup_ca.entities.CommentUpVote;
import com.hotmail.dolzhik.zalup_ca.entities.User;
import com.hotmail.dolzhik.zalup_ca.services.CommentService;
import com.hotmail.dolzhik.zalup_ca.services.UserService;
import com.hotmail.dolzhik.zalup_ca.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AddCommentUpVoteController {

    private final UserService userService;
    private final VoteService voteService;
    private final CommentService commentService;

    @Autowired
    public AddCommentUpVoteController(UserService userService, VoteService voteService, CommentService commentService) {
        this.userService = userService;
        this.voteService = voteService;
        this.commentService = commentService;
    }

    @PostMapping(value = "/upVote/{id}")
    ResponseEntity addUpVote(@PathVariable(name = "id") Integer commentId, Principal principal) {
        User user = userService.findByLogin(principal.getName());
        Comment comment = commentService.getCommentById(commentId);
        Map<Object,Object> map = new HashMap<>();

        if (comment != null && !voteService.isCommentUpVotedByUser(user.getId(),comment.getId())) {
            CommentUpVote vote = new CommentUpVote();
            vote.setUser(user);
            vote.setComment(comment);
            voteService.addVote(vote);
            map.put("message","UpVote added.");
            return new ResponseEntity<>(map,HttpStatus.OK);
        }

        map.put("message","UpVote has not been added.");
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

}
