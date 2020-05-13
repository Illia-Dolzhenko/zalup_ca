package com.hotmail.dolzhik.zalup_ca.controllers;

import com.hotmail.dolzhik.zalup_ca.dto.CreatePostDto;
import com.hotmail.dolzhik.zalup_ca.dto.ZalupcaResponse;
import com.hotmail.dolzhik.zalup_ca.dto.request.DeletePostRequest;
import com.hotmail.dolzhik.zalup_ca.entities.Post;
import com.hotmail.dolzhik.zalup_ca.entities.User;
import com.hotmail.dolzhik.zalup_ca.services.ICaptchaService;
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
    private final ICaptchaService captchaService;

    @Autowired
    public PostController(UserService userService, PostService postService, ICaptchaService captchaService) {
        this.userService = userService;
        this.postService = postService;
        this.captchaService = captchaService;
    }

    @PostMapping(value = "/createPost")
    ResponseEntity createPost(@Valid @RequestBody CreatePostDto createPostDto, Principal principal) {
        if(captchaService.isValid(createPostDto.getToken(),"createPost")) {
            User user = userService.findByLogin(principal.getName());
            if (user.getPoints() >= Constants.POST_COST) {
                Post post = new Post();
                post.setUser(user);
                post.setImage(createPostDto.getImage());
                post.setTimeToLive(createPostDto.getTimeToLive());
                post.setCreationDate(new Timestamp(new Date().getTime()));
                post.setText(createPostDto.getText());
                post = postService.createPost(post);
                userService.changePoints(user, -Constants.POST_COST);
                return new ResponseEntity<>(post, HttpStatus.OK);
            }
            return new ResponseEntity<>(new ZalupcaResponse("You dont have enough points to create a post."), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ZalupcaResponse("Captcha is invalid."), HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/getPosts", produces = "application/json")
    ResponseEntity getPosts() {
        List<Post> posts = postService.findAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping(value = "/getMyPosts")
    ResponseEntity getMyPosts(Principal principal) {
        String currentUser = principal.getName();
        return new ResponseEntity<>(postService.findPostsByUserLogin(currentUser),HttpStatus.OK);
    }

    @GetMapping(value = "/getPost/{id}")
    ResponseEntity getPost(@PathVariable(name = "id") Integer id) {
        Post post = postService.findPostById(id);
        if (post != null) {
            return new ResponseEntity<>(post, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ZalupcaResponse("Post with id: " + id + " does not exist."), HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/deletePost")
    ResponseEntity deletePost(@RequestBody @Valid DeletePostRequest request, Principal principal){
        Post postToDelete = postService.findPostById(request.getPostId());

        if(postToDelete.getUser().getLogin().equals(principal.getName())){
            postService.deletePost(postToDelete);
            return new ResponseEntity<>(new ZalupcaResponse("Post has been deleted."),HttpStatus.OK);
        }

        return new ResponseEntity<>(new ZalupcaResponse("Wrong post id."),HttpStatus.BAD_REQUEST);
    }

}
