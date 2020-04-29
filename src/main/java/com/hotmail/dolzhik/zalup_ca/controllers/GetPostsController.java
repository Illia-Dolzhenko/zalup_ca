package com.hotmail.dolzhik.zalup_ca.controllers;

import com.hotmail.dolzhik.zalup_ca.entities.Post;
import com.hotmail.dolzhik.zalup_ca.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GetPostsController {

    private final PostService postService;

    @Autowired
    public GetPostsController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(value = "/getPosts", produces = "application/json")
    ResponseEntity getPosts() {
        List<Post> posts = postService.findAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping(value = "getPost/{id}")
    ResponseEntity getPost(@PathVariable(name = "id") Integer id) {
        Post post = postService.findPostById(id);
        if (post != null) {
            return new ResponseEntity<>(post,HttpStatus.OK);
        }
        Map<Object,Object> response = new HashMap<>();
        response.put("message","Post with id: " + id + " does not exist.");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
