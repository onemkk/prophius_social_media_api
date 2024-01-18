package com.prophius.service.impl;
import com.prophius.dto.CommentDto;
import com.prophius.dto.response.DeleteResponseData;
import com.prophius.entity.Comment;
import com.prophius.entity.Post;
import com.prophius.entity.User;
import com.prophius.mapper.Mapper;
import com.prophius.repository.CommentRepository;
import com.prophius.service.CommentService;
import com.prophius.service.PostService;
import com.prophius.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final UserService userService;

    private final PostService postService;


    public Long saveComment(CommentDto comment, Long userId, Long postId) {
        System.out.println("post = " + postId);
        User user = userService.getUserById(userId);
        Post post = postService.getPostById(postId);

        // save comment to database
        Comment newcomment = Comment.builder().
                commentContent(comment.getComment_body()).
                post(post).user(user).build();
        System.out.println("comment is saved");
        newcomment = commentRepository.save(newcomment);
        return newcomment.getCommentId();
    }

    public List<CommentDto> getCommentsByPost(Long post_id){
        Post post = postService.getPostById(post_id);
        return commentRepository.findAllByPost(post).stream().map(Mapper::toCommentDto).toList();
    }

    public Comment getCommentById(Long comment_id){
        return commentRepository.getById(comment_id);
    }

    public DeleteResponseData<CommentDto> deleteCommentByID(Long comment_id){
        Comment comment = getCommentById(comment_id);
        commentRepository.deleteById(comment_id);
        CommentDto commentObj = CommentDto.builder().comment_body(comment.getCommentContent()).build();

        return DeleteResponseData.<CommentDto>builder().obj(commentObj).deleted(true).build();
    }
}
