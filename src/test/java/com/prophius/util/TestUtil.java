package com.prophius.util;

import com.prophius.dto.request.UserRegistrationRequest;
import com.prophius.dto.response.AuthenticationResponse;
import com.prophius.dto.response.RoleResponse;
import com.prophius.dto.response.UserResponse;
import com.prophius.entity.Permission;
import com.prophius.entity.Role;
import com.prophius.entity.User;
import com.prophius.enums.RoleName;
import com.prophius.enums.UserType;

import java.util.List;
import java.util.Set;

public class TestUtil {

    public AuthenticationResponse getAuthenticationResponse() {

        return AuthenticationResponse.builder()
                .accessToken("e29Y8DYmn0M7j7J89/TQQXMpMQ0GHBaQUDQfJYvNOLLmXEhF9AByXUhY0gzZmTHa")
                .refreshToken("sMUpGdeBv3De6m/WptP4iniOGmASD2qnFbW+aAosDUFq1yCEdCfc0z7EPQIDAQAB")
                .tokenType("bearer")
                .expiresIn(1800)
                .build();
    }

    public UserRegistrationRequest getUserRegistrationRequest() {

        return UserRegistrationRequest.builder()
                .userName("John")
                .email("john.doe@starter.com")
                .password("password")
                .type(UserType.ADMIN)
                .build();
    }

    public UserResponse getUserResponse() {

        return UserResponse.builder()
                .id(1L)
                .username("john")
                .roles(List.of(RoleResponse.builder()
                        .id(2L)
                        .name(RoleName.ROLE_ADMIN)
                        .permissions(List.of("users:read"))
                        .build()))
                .build();
    }

    public Role getRole() {

        return Role.builder()
                .id(1L)
                .description("Role with admin permissions")
                .name(RoleName.ROLE_ADMIN)
                .permissions(this.getPermissions())
                .build();
    }

    private Set<Permission> getPermissions() {

        return Set.of(
                Permission.builder()
                        .name("user:read")
                        .description("Ability to fetch all users")
                        .build(),
                Permission.builder()
                        .name("user:write")
                        .description("Ability to create a user")
                        .build()
        );
    }

    public User getUser() {

        return User.builder()
                .id(1L)
                .username("John")
                .email("john.doe@starter.com")
                .roles(Set.of(this.getRole()))
                .build();
    }
}
