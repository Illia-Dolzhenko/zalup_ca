package com.hotmail.dolzhik.zalup_ca.repositories;

import com.hotmail.dolzhik.zalup_ca.entities.Rating;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RatingRepository extends CrudRepository<Rating,Integer> {
    List<Rating> findRatingsByUserIdAndPostId(Integer userId, Integer postId);
}
