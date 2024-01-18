package com.prophius.controller;

import com.prophius.common.ResponseObject;
import com.prophius.dto.response.ConnectionResponse;
import com.prophius.dto.response.UserResponse;
import com.prophius.dto.request.UserRegistrationRequest;
import com.prophius.entity.User;
import com.prophius.mapper.Mapper;
import com.prophius.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseObject<UserResponse> registerUser(@RequestBody @Valid UserRegistrationRequest userRegistrationRequest){
        log.info("Received user registration request: {}", userRegistrationRequest);

        return ResponseObject.<UserResponse>builder()
                .status(ResponseObject.ResponseStatus.SUCCESSFUL)
                .message("User registered successfully")
                .data(userService.registerUser(userRegistrationRequest))
                .build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('role_admin')")
    public ResponseObject<Page<UserResponse>> getAllUsers(@PageableDefault(
                                                            sort = "id", direction = Sort.Direction.DESC
                                                          ) Pageable pageable){

        return ResponseObject.<Page<UserResponse>>builder()
                .status(ResponseObject.ResponseStatus.SUCCESSFUL)
                .data(userService.getAllUsers(pageable))
                .build();
    }

    @GetMapping("/getconnections/{userId}")
    public ResponseObject<List<UserResponse>>  getAllConnections ( @PathVariable long userId){

     return  ResponseObject.<List<UserResponse>>builder()
                .status(ResponseObject.ResponseStatus.SUCCESSFUL)
             .message("fetched connections successfully")
                .data(userService.getMyConnections(userId).stream().map(Mapper::toUserResponse).toList())
                .build();
    }


    @PostMapping("/{userId}/connection/{connectingUserId}")
    public ResponseObject<ConnectionResponse> createConnection (@PathVariable long userId, @PathVariable long connectingUserId){
        return  ResponseObject.<ConnectionResponse>builder()
                .status(ResponseObject.ResponseStatus.SUCCESSFUL)
                .message("created connection successfully")
                .data(userService.createConnectionStatus(userId, connectingUserId))
                .build();
    }
}
