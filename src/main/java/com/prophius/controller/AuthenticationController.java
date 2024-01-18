package com.prophius.controller;

import com.prophius.common.ResponseObject;
import com.prophius.service.AuthenticationService;
import com.prophius.dto.response.AuthenticationResponse;
import com.prophius.dto.request.AuthenticationRequest;
import com.prophius.dto.request.RefreshTokenRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest request) {

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(authenticationService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(@RequestBody @Valid RefreshTokenRequest request){

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(authenticationService.refreshToken(request));
    }

    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseObject logout(Principal principal){

        authenticationService.logout(principal.getName());

        return ResponseObject.builder()
                .status(ResponseObject.ResponseStatus.SUCCESSFUL)
                .message("User logged out successfully")
                .build();
    }
}
