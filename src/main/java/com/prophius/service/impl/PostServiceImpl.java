package com.prophius.service.impl;

import com.prophius.dto.PostDto;
import com.prophius.entity.Post;
import com.prophius.entity.User;
import com.prophius.mapper.Mapper;
import com.prophius.repository.CommentRepository;
import com.prophius.repository.PostRepository;
import com.prophius.repository.UserRepository;
import com.prophius.service.PostService;
import com.prophius.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final UserService userService;

    @Override
    public Page<PostDto> getAllPost(Pageable pageable) {

        return postRepository.findAll(pageable).map(Mapper::toPostDto);
    }

    @Transactional
    public Long createPost(PostDto postDto){
        // save post to database
        User user = userService.getUserById(postDto.getUser_id());
        Post post = Post.builder().postContent(postDto.getPostContent()).user(user).build();
        post = postRepository.save(post);
        System.out.println("post is saved");
        return post.getPost_id();
    }


    @Transactional
    public PostDto updatePost (Long userId, Long postId, String postBody) throws Exception {
        User user = userService.getUserById(userId);
        Post post = getPostById(postId);

        if (post.getUser().equals(user)){
            try {
                post.setPostContent(postBody);
                postRepository.save(post);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("post is saved");
            return PostDto.builder().postContent(postBody).user_id(userId).build();
        }else {
            throw new Exception("This user did not create this post");
        }

    }

    public Post getPostById(long postId) {
        Optional< Post > optional = postRepository.findById(postId);
        if (optional.isPresent()) {
           return optional.get();
        } else {
            throw new RuntimeException(" Post not found for id :: " + postId);
        }
    }

    public List<PostDto> getPostByConnections(long userId){
        List<User> users = userService.getMyConnections(userId);
        List<List<Post>> listofpostlists = new ArrayList<>();
        for (User user : users) {
            listofpostlists.add(postRepository.getPostsByUser(user));
        }
        List<Post> flatList = new ArrayList<>();
        listofpostlists.forEach(flatList::addAll);
        return flatList.stream().map(Mapper::toPostDto).toList();
    }

    @Transactional
    public Post likePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));

        Set<User> userSet = post.getLikedByUsers();
        Set<Post> postSet = user.getLikedPosts();
//        boolean removeUser = userSet.removeIf(u -> u.equals(user));
        System.out.println("user removal ");
        if (userSet.contains(user)){
            userSet.remove(user);
            postSet.remove(post);
        }else{
            userSet.add(user);
            postSet.add(post);
        }
        user.setLikedPosts(postSet);
        post.setLikedByUsers(userSet);
        userRepository.save(user);
        postRepository.save(post);
        return post;
    }


//    @Transactional
//    public void deletePostById(long postId) {
//        commentRepository.deleteAllpost(postId);
//        postRepository.deleteById(postId);
//    }
}
