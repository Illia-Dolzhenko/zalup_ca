package com.hotmail.dolzhik.zalup_ca.services;

import com.hotmail.dolzhik.zalup_ca.entities.Comment;

import java.util.List;

public interface CommentService {

    void addComment(Comment comment);
    List<Comment> getComments();
    List<Comment> getCommentsForPostId(Integer id);
    Comment getCommentById(Integer id);

}
