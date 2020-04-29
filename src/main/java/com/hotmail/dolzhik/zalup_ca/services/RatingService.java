package com.hotmail.dolzhik.zalup_ca.services;

import com.hotmail.dolzhik.zalup_ca.entities.Rating;

public interface RatingService {
    void addRating(Rating rating);
    Rating getRatingByUserAndPostId(Integer userId,Integer postId);
}
