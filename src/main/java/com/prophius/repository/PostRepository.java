package com.prophius.repository;

import com.prophius.entity.Post;
import com.prophius.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> getPostsByUser(User user);
}
