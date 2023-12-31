package com.myblog1.controller;

import com.myblog1.exception.ResourceNotFound;
import com.myblog1.payload.PostResponse;
import com.myblog1.payload.PostDto;
import com.myblog1.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //http://localhost:8080/api/posts/{id}
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> savePosts(@Valid @RequestBody PostDto postDto, BindingResult result) {
           if (result.hasErrors()) {
               return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
           }

           PostDto dto = postService.savePost(postDto);
           return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }



   @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("post is deleted",HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<PostDto> updatePost(
            @RequestParam("id")long id,
            @RequestBody PostDto dto
    ){
     PostDto postDto= postService.updatePost(id,dto);
     return new ResponseEntity<>(postDto,HttpStatus.OK);
    }

    //http://localhost:8080/api/posts?pageNo=1&pageSize=5&sortBy=title&sortDir=asc
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(name="pageNo",required = false,defaultValue = "1")int pageNo,
            @RequestParam(name="pageSize",required = false,defaultValue ="5")int pageSize,
            @RequestParam(name="sortBy",required = false,defaultValue ="id")String sortBy,
            @RequestParam(name="sortDir",required = false,defaultValue ="asc")String sortDir
    ){
   PostResponse response= postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
    return response;
    }
}
