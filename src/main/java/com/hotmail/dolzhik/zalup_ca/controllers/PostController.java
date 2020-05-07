package com.hotmail.dolzhik.zalup_ca.controllers;

import com.hotmail.dolzhik.zalup_ca.dto.CreatePostDto;
import com.hotmail.dolzhik.zalup_ca.dto.ZalupcaResponse;
import com.hotmail.dolzhik.zalup_ca.entities.Post;
import com.hotmail.dolzhik.zalup_ca.entities.User;
import com.hotmail.dolzhik.zalup_ca.services.PostService;
import com.hotmail.dolzhik.zalup_ca.services.UserService;
import com.hotmail.dolzhik.zalup_ca.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@RestController
public class PostController {

    private final UserService userService;
    private final PostService postService;

    @Autowired
    public PostController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @PostMapping(value = "/createPost")
    ResponseEntity createPost(@Valid @RequestBody CreatePostDto createPostDto, Principal principal) {
        User user = userService.findByLogin(principal.getName());

        if (user.getPoints() >= Constants.POST_COST) {
            Post post = new Post();
            post.setUser(user);
            post.setImage(createPostDto.getImage());
            post.setTimeToLive(createPostDto.getTimeToLive());
            post.setCreationDate(new Timestamp(new Date().getTime()));
            post.setText(createPostDto.getText());
            post = postService.createPost(post);
            userService.changePoints(user,-Constants.POST_COST);
            return new ResponseEntity<>(post, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ZalupcaResponse("You dont have enough points to create a post."), HttpStatus.OK);
    }

    @GetMapping(value = "/getPosts", produces = "application/json")
    ResponseEntity getPosts() {
        List<Post> posts = postService.findAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping(value = "/getPost/{id}")
    ResponseEntity getPost(@PathVariable(name = "id") Integer id) {
        Post post = postService.findPostById(id);
        if (post != null) {
            return new ResponseEntity<>(post, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ZalupcaResponse("Post with id: " + id + " does not exist."), HttpStatus.NOT_FOUND);
    }

}
