package com.choi.p67260811.domain.post.comment.dto;

import com.choi.p67260811.domain.post.comment.entity.PostComment;

import java.time.LocalDateTime;

public record PostCommentDto(
        int id,
        String comment,
        LocalDateTime createDate,
        LocalDateTime modifyDate
) {
    public PostCommentDto(PostComment postComment){
        this(
                postComment.getId(),
                postComment.getContent(),
                postComment.getCreateDate(),
                postComment.getModifyDate()
        );
    }
}
