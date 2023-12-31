package com.myblog1.controller;

import com.myblog1.payload.CommentDto;
import com.myblog1.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    CommentService commentService;

    public CommentController( CommentService commentService) {
        this.commentService = commentService;
    }

    //http://localhost:8080/api/comments?postId=1
    @PostMapping
    public ResponseEntity<CommentDto>createComments(
            @RequestParam("postId") long postId, @RequestBody CommentDto dto){
       CommentDto commentDto= commentService.createComment(postId,dto);
       return  new ResponseEntity<CommentDto>(commentDto, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteComments(
            @RequestParam("postId") long postId,
            @RequestParam("commentId")long commentId
    ){
        commentService.delteComment(postId,commentId);
        return  new ResponseEntity<String>("Comment is deleted",HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(
            @RequestParam("postId") long postId
    ){
      List<CommentDto> commentDtos= commentService.getAllCommentsBasedOnPostId(postId);
      return new ResponseEntity<List<CommentDto>>(commentDtos,HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CommentDto> updateCommentBasedOnId(
            @RequestParam("postId")long postId,
            @RequestParam("commentId")long commentId,
            @RequestBody CommentDto commentDto
    ){

      CommentDto commentDtos=  commentService.upadateComment(postId,commentId,commentDto);
      return new ResponseEntity<>(commentDtos,HttpStatus.OK);
    }
}
