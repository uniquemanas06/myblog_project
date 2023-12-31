package com.myblog1.service.impl;

import com.myblog1.entity.Post;
import com.myblog1.payload.PostResponse;
import com.myblog1.exception.ResourceNotFound;
import com.myblog1.payload.PostDto;
import com.myblog1.repository.PostRepository;
import com.myblog1.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepo;

    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepo,ModelMapper modelMapper) {

        this.postRepo = postRepo;
        this.modelMapper=modelMapper;
    }

    @Override
    public PostDto savePost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        postRepo.save(post);
        PostDto dto = mapToDto(post);
        return dto;
    }

    @Override
    public void deletePostById(long id) {
        Post post=  postRepo.findById(id).orElseThrow(
                ()->new ResourceNotFound("Post is not found with id "+id)
        );
        postRepo.deleteById(id);
    }

    @Override
    public PostDto updatePost(long ids, PostDto dto) {
      Post post=  postRepo.findById(ids).orElseThrow(
                ()->new ResourceNotFound("post is not found with id "+ids)
        );
      post.setTitle(dto.getTitle());
      post.setDescription(dto.getDescription());
      post.setContent(dto.getContent());
        Post savedPost = postRepo.save(post);
        return mapToDto(savedPost);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
       Sort sort= sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();
      Pageable pageable= PageRequest.of(pageNo,pageSize,sort);
        Page<Post> pageOfPosts = postRepo.findAll(pageable);
        List<Post> posts = pageOfPosts.getContent();
        List<PostDto> dtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
       PostResponse postResponse = new PostResponse();
       postResponse.setPostDto(dtos);
       postResponse.setPageNo(pageOfPosts.getNumber());
       postResponse.setPageSize(pageOfPosts.getSize());
       postResponse.setLastPage(pageOfPosts.isLast());
       postResponse.setTotalPages(pageOfPosts.getTotalPages());
       return postResponse;
    }

    public Post mapToEntity(PostDto dto){
        Post post = new Post();
       Post savedPost=  modelMapper.map(dto,Post.class);
//       post.setTitle(dto.getTitle());
//       post.setDescription(dto.getDescription());
//       post.setContent(dto.getContent());
//        Post savedPost = postRepo.save(post);
        return savedPost;
    }

    public PostDto mapToDto(Post post){
      PostDto postDto = new PostDto();
        PostDto dto = modelMapper.map(post, PostDto.class);
        //postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());
        return dto;
    }
}
