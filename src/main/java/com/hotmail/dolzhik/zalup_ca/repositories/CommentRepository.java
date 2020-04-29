package com.hotmail.dolzhik.zalup_ca.repositories;

import com.hotmail.dolzhik.zalup_ca.entities.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment,Integer> {
    List<Comment> findCommentsByPost_Id(Integer id);
}
