package com.prophius.controller;


import com.prophius.common.ResponseObject;
import com.prophius.dto.CommentDto;
import com.prophius.dto.response.DeleteResponseData;
import com.prophius.entity.Comment;
import com.prophius.entity.Post;
import com.prophius.entity.User;
import com.prophius.service.CommentService;
import com.prophius.service.PostService;
import com.prophius.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    private final PostService postService;

    private final UserService userService;

    @PostMapping("/createcomment/{userId}/{postId}")
    public ResponseObject<Long> saveComment(@RequestBody CommentDto commentDto, @PathVariable long userId, @PathVariable long postId) {
        return ResponseObject.<Long>builder()
                .status(ResponseObject.ResponseStatus.SUCCESSFUL)
                .message("Comment was created successfully for: "+ postId)
                .data(commentService.saveComment(commentDto, userId, postId))
                .build();
    }

    @Transactional
    @DeleteMapping("/deletecomment/{commentId}")
    public ResponseObject<DeleteResponseData<CommentDto>> deleteComment (@PathVariable long commentId){
        return ResponseObject.<DeleteResponseData<CommentDto>>builder()
                .status(ResponseObject.ResponseStatus.SUCCESSFUL)
                .data(commentService.deleteCommentByID(commentId))
                .build();

    }

    @GetMapping("/getcommentsbypost/{postID}")
    public ResponseObject<List<CommentDto>> getCommentByPost(@PathVariable long postID){
        return ResponseObject.<List<CommentDto>>builder()
                .status(ResponseObject.ResponseStatus.SUCCESSFUL)
                .message("fetched comments of post: "+ postID)
                .data(commentService.getCommentsByPost(postID))
                .build();
    }



}
