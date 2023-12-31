package com.myblog1.service;

import com.myblog1.payload.CommentDto;

import java.util.List;

public interface CommentService {
    public CommentDto createComment(long postId, CommentDto dto);

    void delteComment(long postId, long commentId);

    List<CommentDto> getAllCommentsBasedOnPostId(long postId);

    CommentDto upadateComment(long postId, long commentId,CommentDto commentDto);
}
