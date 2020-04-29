package com.hotmail.dolzhik.zalup_ca.controllers;

import com.hotmail.dolzhik.zalup_ca.dto.CommentDto;
import com.hotmail.dolzhik.zalup_ca.entities.Comment;
import com.hotmail.dolzhik.zalup_ca.entities.Post;
import com.hotmail.dolzhik.zalup_ca.entities.User;
import com.hotmail.dolzhik.zalup_ca.services.CommentService;
import com.hotmail.dolzhik.zalup_ca.services.PostService;
import com.hotmail.dolzhik.zalup_ca.services.UserService;
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
import java.util.HashMap;
import java.util.Map;

@RestController
public class AddComment {

    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;

    @Autowired
    public AddComment(CommentService commentService, PostService postService, UserService userService) {
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping(value = "/addComment")
    ResponseEntity addComment(@Valid @RequestBody CommentDto commentDto, Principal principal){

        User user = userService.findByLogin(principal.getName());
        Post post = postService.findPostById(commentDto.getPostId());
        Map<Object,Object> map = new HashMap<>();
        if(post!=null){
            Comment comment = new Comment();
            comment.setText(commentDto.getText());
            comment.setCreated(new Timestamp(new Date().getTime()));
            comment.setUser(user);
            comment.setPost(post);
            commentService.addComment(comment);
            System.out.println(post.getId());
            map.put("Message","Comment has been added.");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        map.put("Message","Comment has not been added.");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
