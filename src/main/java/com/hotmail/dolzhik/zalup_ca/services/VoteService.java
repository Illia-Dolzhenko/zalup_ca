package com.hotmail.dolzhik.zalup_ca.services;

import com.hotmail.dolzhik.zalup_ca.entities.CommentUpVote;

public interface VoteService {

    void addVote(CommentUpVote vote);
    int countVotesForCommentById(Integer id);
    boolean isCommentUpVotedByUser(Integer userId, Integer commentId);

}
