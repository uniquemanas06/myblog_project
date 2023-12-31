package com.myblog1.service;

import com.myblog1.payload.PostResponse;
import com.myblog1.payload.PostDto;

public interface  PostService {
    public PostDto savePost(PostDto postDto);

    void deletePostById(long id);

    PostDto updatePost(long ids, PostDto dto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
}
