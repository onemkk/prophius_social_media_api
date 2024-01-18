package com.prophius.repository;

import com.prophius.entity.Comment;
import com.prophius.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
//    @Query ("select c from Comment c where c.post.post_id = ?1")
//    List<Comment> findAllByPost_Post_id(Long post_id);

    List<Comment> findAllByPost(Post post);

    @Modifying
    @Query("delete from Comment b where b.post.post_id=:postid")
    void deleteAllpost(@Param("postid") Long postid);
}
