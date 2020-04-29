package com.hotmail.dolzhik.zalup_ca.controllers;

import com.hotmail.dolzhik.zalup_ca.dto.CreatePostDto;
import com.hotmail.dolzhik.zalup_ca.entities.Post;
import com.hotmail.dolzhik.zalup_ca.entities.User;
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
public class CreatePostController {

    private final UserService userService;
    private final PostService postService;

    @Autowired
    public CreatePostController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @PostMapping(value = "/createPost")
    ResponseEntity createPost(@Valid @RequestBody CreatePostDto createPostDto, Principal principal) {
        User user = userService.findByLogin(principal.getName());
        Post post = new Post();
        post.setUser(user);
        post.setImage(createPostDto.getImage());
        post.setTimeToLive(createPostDto.getTimeToLive());
        post.setCreationDate(new Timestamp(new Date().getTime()));
        post = postService.createPost(post);

        Map<Object, Object> map = new HashMap<>();
        map.put("Message", "Post has been added.");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
