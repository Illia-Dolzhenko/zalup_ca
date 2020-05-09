package com.hotmail.dolzhik.zalup_ca.controllers;

import com.hotmail.dolzhik.zalup_ca.dto.ZalupcaResponse;
import com.hotmail.dolzhik.zalup_ca.dto.request.CaptchaToken;
import com.hotmail.dolzhik.zalup_ca.entities.Comment;
import com.hotmail.dolzhik.zalup_ca.entities.Post;
import com.hotmail.dolzhik.zalup_ca.entities.Rating;
import com.hotmail.dolzhik.zalup_ca.entities.User;
import com.hotmail.dolzhik.zalup_ca.repositories.CommentRepository;
import com.hotmail.dolzhik.zalup_ca.repositories.PostRepository;
import com.hotmail.dolzhik.zalup_ca.repositories.RatingRepository;
import com.hotmail.dolzhik.zalup_ca.repositories.UserRepository;
import com.hotmail.dolzhik.zalup_ca.services.ICaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
public class TestController {


    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ICaptchaService captchaService;

    @PostMapping("/admin/removePost/{id}")
    public String test(@PathVariable(name = "id") Integer postId){

        Post post = postRepository.findById(postId).orElse(null);

        if(post!=null){
            postRepository.delete(post);
            return "post deleted";
        }

        return "error";
    }

    @PostMapping(value = "/test")
    public ResponseEntity captchaTest(@RequestBody CaptchaToken token){

        System.out.println(token);
        captchaService.isValid(token.getToken(),"test");

        return new ResponseEntity<>(new ZalupcaResponse("test"), HttpStatus.OK);
    }
}
