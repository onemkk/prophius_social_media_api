package com.prophius.service.impl;

import com.prophius.dto.response.ConnectionResponse;
import com.prophius.dto.response.UserResponse;
import com.prophius.dto.request.UserRegistrationRequest;
import com.prophius.entity.Connections;
import com.prophius.entity.Role;
import com.prophius.entity.User;
import com.prophius.enums.RoleName;
import com.prophius.enums.UserType;
import com.prophius.mapper.Mapper;
import com.prophius.repository.ConnectionRepository;
import com.prophius.service.UserService;
import com.prophius.repository.UserRepository;
import com.prophius.service.RoleService;
import jakarta.persistence.EntityExistsException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    private final ConnectionRepository connectionRepository;

    @Override
    public UserResponse registerUser(UserRegistrationRequest userRegistrationRequest){

        if(userRepository.existsByUsername(userRegistrationRequest.getEmail()))
            throw new EntityExistsException(String.format("Email %s already exists", userRegistrationRequest.getEmail()));

        Role role = roleService.getRoleByUserType(userRegistrationRequest.getType());

        User user = User.builder()
                .username(userRegistrationRequest.getUserName())
                .password(passwordEncoder.encode(userRegistrationRequest.getPassword()))
                .email(userRegistrationRequest.getEmail())
                .roles(Collections.singleton(role))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();

        if(userRegistrationRequest.getType().equals(UserType.ADMIN)){
            user.setVerified(true);
        }

        return Mapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable){

        return userRepository.findAll(pageable).map(Mapper::toUserResponse);
    }

    public ConnectionResponse createConnectionStatus(long userId, long connectingUserId){
        ConnectionResponse connectionResponse = new ConnectionResponse();
        if (createConnection(userId, connectingUserId)){
            connectionResponse.setUser(getUserById(userId));
            connectionResponse.setUser2(getUserById(connectingUserId));
            connectionResponse.setStatus(true);
        }
        else{
            connectionResponse.setStatus(false);
        }
        return connectionResponse;
    }
    public Boolean createConnection(long loggedInUserId, long connectingUserId) {
        User connectingUser = userRepository.findUserById(connectingUserId);
        if (connectingUser == null){
            return false;
        }
        if (loggedInUserId == connectingUserId){
            return false;
        }
        else {
            List<Connections> loggedInUserconnections = connectionRepository.findAllByFollowerIdAndFollowee_Id(loggedInUserId, loggedInUserId);

            for (Connections loggedInUserconnection : loggedInUserconnections) {
                if (loggedInUserconnection.getFollowerId() == connectingUserId || loggedInUserconnection.getFolloweeId() == connectingUserId) {
                    return false;
                }
            }
            Connections connection = new Connections();
            connection.setFollowerId(loggedInUserId);
            connection.setFolloweeId(connectingUserId);
            connectionRepository.save(connection);
        }
        return true;
    }



    public List<User> getMyConnections( long userId){
        List<Connections> connections = connectionRepository.findAllByFollowerIdAndFollowee_Id( userId, userId);
        List<User> users = new ArrayList<User>();
        for (Connections connection : connections) {
            if (connection.getFollowerId() == userId) {
                users.add(userRepository.findUserById(connection.getFollowerId()));
            }
            if (connection.getFolloweeId() == userId) {
                users.add(userRepository.findUserById(connection.getFollowerId()));
            }
        }
        return users;
    }

    public User getUserById(long id) {
        return userRepository.findUserById(id);
    }
}
