package com.prophius.service;

import com.prophius.dto.response.ConnectionResponse;
import com.prophius.dto.response.UserResponse;
import com.prophius.dto.request.UserRegistrationRequest;
import com.prophius.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    UserResponse registerUser(UserRegistrationRequest userRegistrationRequest);

    Page<UserResponse> getAllUsers(Pageable pageable);

    Boolean createConnection(long loggedInUser, long connectingUser);

    User getUserById(long id);

    List<User> getMyConnections( long userId);

    ConnectionResponse createConnectionStatus(long userId, long connectingUserId);
}
