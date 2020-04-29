package com.hotmail.dolzhik.zalup_ca.services.impl;

import com.hotmail.dolzhik.zalup_ca.entities.CommentUpVote;
import com.hotmail.dolzhik.zalup_ca.repositories.VoteRepository;
import com.hotmail.dolzhik.zalup_ca.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;

    @Autowired
    public VoteServiceImpl(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Override
    public void addVote(CommentUpVote vote) {
        voteRepository.save(vote);
    }

    @Override
    public int countVotesForCommentById(Integer id) {
        return voteRepository.countCommentUpVoteByComment_Id(id);
    }

    @Override
    public boolean isCommentUpVotedByUser(Integer userId, Integer commentId) {
        return voteRepository.countCommentUpVoteByComment_IdAndUser_Id(commentId,userId)>0;
    }
}
