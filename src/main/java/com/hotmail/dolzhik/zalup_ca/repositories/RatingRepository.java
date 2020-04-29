package com.hotmail.dolzhik.zalup_ca.repositories;

import com.hotmail.dolzhik.zalup_ca.entities.Rating;
import org.springframework.data.repository.CrudRepository;

public interface RatingRepository extends CrudRepository<Rating,Integer> {
    Rating findRatingByUserIdAndPostId(Integer userId,Integer postId);
}
