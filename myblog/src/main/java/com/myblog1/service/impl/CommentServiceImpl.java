package com.myblog1.service.impl;

import com.myblog1.entity.Comment;
import com.myblog1.entity.Post;
import com.myblog1.exception.ResourceNotFound;
import com.myblog1.payload.CommentDto;
import com.myblog1.repository.CommentRepository;
import com.myblog1.repository.PostRepository;
import com.myblog1.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    PostRepository postRepo;
    CommentRepository commentRepo;

    public CommentServiceImpl(PostRepository postRepo, CommentRepository commentRepo) {
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto dto) {
      Post post=  postRepo.findById(postId).orElseThrow(
                ()->new ResourceNotFound("post is not found with id "+postId)
        );
        Comment comments = mapToEntity(dto);
        comments.setPost(post);
        commentRepo.save(comments);
       return mapToDto(comments);
    }

    @Override
    public void delteComment(long postId, long commentId) {
       Post post= postRepo.findById(postId).orElseThrow(
                ()->new ResourceNotFound("Post is not found with id "+postId)
        );
      Comment comment= commentRepo.findById(commentId).orElseThrow(
               ()->new ResourceNotFound("Comment is not found with id "+commentId)
       );
      commentRepo.deleteById(commentId);
    }

    @Override
    public List<CommentDto> getAllCommentsBasedOnPostId(long postId) {
      Post post= postRepo.findById(postId).orElseThrow(
              ()->new ResourceNotFound("Post is not found with id "+postId)
       );
        List<Comment> comments = commentRepo.findByPostId(postId);
        List<CommentDto> commentDtos = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
     return  commentDtos;
    }

    @Override
    public CommentDto upadateComment(long postId, long commentId,CommentDto dto) {
       Post post= postRepo.findById(postId).orElseThrow(
                ()->new ResourceNotFound("post is not found with id "+postId)
        );
    Comment comment=   commentRepo.findById(commentId).orElseThrow(
               ()->new ResourceNotFound("comment is not found with id "+commentId)
       );
     comment.setBody(dto.getBody());
     comment.setName(dto.getName());
     comment.setEmail(dto.getEmail());
        Comment updatedComment = commentRepo.save(comment);
        CommentDto commentDto = mapToDto(updatedComment);
        return commentDto;
    }

    public Comment mapToEntity(CommentDto dto){
        Comment comment = new Comment();
        comment.setBody(dto.getBody());
        comment.setName(dto.getName());
        comment.setEmail(dto.getEmail());
        return comment;
    }
    public CommentDto mapToDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setBody(comment.getBody());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        return commentDto;
    }
}
