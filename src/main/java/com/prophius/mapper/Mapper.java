package com.prophius.mapper;

import com.prophius.dto.CommentDto;
import com.prophius.dto.CustomUserDetails;
import com.prophius.dto.PostDto;
import com.prophius.dto.response.RoleResponse;
import com.prophius.dto.response.UserResponse;
import com.prophius.entity.Comment;
import com.prophius.entity.Post;
import com.prophius.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

public class Mapper {

    public static CustomUserDetails toCustomUserDetails(User user){

        CustomUserDetails customUserDetails = new CustomUserDetails();
        BeanUtils.copyProperties(user, customUserDetails);

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        user.getRoles().forEach(r -> {
            authorities.add(new SimpleGrantedAuthority(r.getName().getValue()));

            r.getPermissions().forEach(p ->  {
                if(user.isVerified()){
                    authorities.add(new SimpleGrantedAuthority(p.getName()));
                }else if(!p.isRequiresVerification()){
                    authorities.add(new SimpleGrantedAuthority(p.getName()));
                }
            });
        });

        customUserDetails.setAuthorities(authorities);
        return customUserDetails;
    }

    public static UserResponse toUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse, "roles");

        List<RoleResponse> roles = new ArrayList<>();

        user.getRoles().forEach(role -> {
            RoleResponse roleResponse = new RoleResponse();
            BeanUtils.copyProperties(role, roleResponse, "permissions");

            List<String> permissions = new ArrayList<>();
            role.getPermissions().forEach(p -> permissions.add(p.getName()));
            roleResponse.setPermissions(permissions);
            roles.add(roleResponse);
        });

        userResponse.setRoles(roles);
        return userResponse;
    }

    public static PostDto toPostDto(Post post){
        return PostDto.builder().postContent(post.getPostContent()).user_id(post.getUser().getId()).build();
    }

    public static CommentDto toCommentDto(Comment comment){
        return CommentDto.builder().comment_body(comment.getCommentContent()).build();
    }
}
