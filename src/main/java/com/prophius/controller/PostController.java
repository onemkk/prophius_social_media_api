package com.prophius.controller;

import com.prophius.common.ResponseObject;
import com.prophius.dto.PostDto;
import com.prophius.entity.Post;
import com.prophius.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    @Autowired
    private final PostService postService;


    @PostMapping("/createpost")
    public ResponseObject<Long> createPost(@RequestBody PostDto postdto) {
        // save post to database
        return ResponseObject.<Long>builder()
                .status(ResponseObject.ResponseStatus.SUCCESSFUL)
                .message("created post successfully")
                .data(postService.createPost(postdto))
                .build();
    }

    @GetMapping("/getallposts")
    public ResponseObject<Page<PostDto>> getAllPosts(Pageable pageable){
        return ResponseObject.<Page<PostDto>>builder()
                .status(ResponseObject.ResponseStatus.SUCCESSFUL)
                .data(postService.getAllPost(pageable))
                .build();

    }


    @PutMapping("/{postId}")
    public ResponseObject<PostDto> updatePost(@RequestBody PostDto post,  @PathVariable long postId) throws Exception {
        // save post to database
        return ResponseObject.<PostDto>builder()
                .status(ResponseObject.ResponseStatus.SUCCESSFUL)
                .message("Post edited successfully")
                .data(postService.updatePost(post.getUser_id(), postId, post.getPostContent()))
                .build();
    }

    @GetMapping("/getpostsbyconnections/{userId}")
    public ResponseObject<List<PostDto>> getPostsByConnections (@PathVariable long userId){
        return ResponseObject.<List<PostDto>>builder()
                .status(ResponseObject.ResponseStatus.SUCCESSFUL)
                .data(postService.getPostByConnections(userId))
                .build();
    }

    @PutMapping("/{postId}/like/{userId}")
    public ResponseObject<String> likePost(@PathVariable Long postId, @PathVariable Long userId) {
        Post post = postService.likePost(postId, userId);
        return ResponseObject.<String>builder()
                .status(ResponseObject.ResponseStatus.SUCCESSFUL)
                .message("Post liked count : "+ post.getLikedByUsers().size())
                .data(post.getPostContent())
                .build();
    }



}
