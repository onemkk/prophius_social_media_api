package com.prophius.service;

import com.prophius.dto.CommentDto;
import com.prophius.dto.response.DeleteResponseData;
import com.prophius.entity.Comment;
import com.prophius.entity.Post;
import com.prophius.entity.User;

import java.util.List;

public interface CommentService {

     List<CommentDto> getCommentsByPost(Long post_id);

     Long saveComment(CommentDto comment, Long userId, Long postId);

     Comment getCommentById(Long comment_id);

     DeleteResponseData<CommentDto> deleteCommentByID(Long comment_id);
}
