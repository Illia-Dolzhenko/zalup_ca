package com.hotmail.dolzhik.zalup_ca.repositories;

import com.hotmail.dolzhik.zalup_ca.entities.CommentUpVote;
import org.springframework.data.repository.CrudRepository;

public interface VoteRepository extends CrudRepository<CommentUpVote,Integer> {
    int countCommentUpVoteByComment_Id(Integer id);
    int countCommentUpVoteByComment_IdAndUser_Id(Integer commentId,Integer userId);
}
