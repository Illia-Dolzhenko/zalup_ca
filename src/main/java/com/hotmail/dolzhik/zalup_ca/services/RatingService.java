package com.hotmail.dolzhik.zalup_ca.services;

import com.hotmail.dolzhik.zalup_ca.entities.Rating;

import java.util.List;

public interface RatingService {
    void addRating(Rating rating);
    List<Rating> getRatingsByUserAndPostId(Integer userId, Integer postId);
}
