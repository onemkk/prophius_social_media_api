package com.prophius.service;

import com.prophius.dto.PostDto;
import com.prophius.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface PostService {
     Page<PostDto> getAllPost(Pageable pageable);

     PostDto updatePost (Long userId, Long postId, String postBody) throws Exception;
     Post getPostById(long postId);

     List<PostDto> getPostByConnections(long userId);

     Post likePost(Long postId, Long userId);

     Long createPost(PostDto postDto);
}
