package com.prophius.service;

import com.prophius.dto.response.AuthenticationResponse;
import com.prophius.dto.request.AuthenticationRequest;
import com.prophius.dto.request.RefreshTokenRequest;

public interface AuthenticationService {

    AuthenticationResponse login(AuthenticationRequest request);

    AuthenticationResponse refreshToken(RefreshTokenRequest request);

    void logout(String username);
}
