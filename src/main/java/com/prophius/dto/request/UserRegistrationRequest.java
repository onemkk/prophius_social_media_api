package com.prophius.dto.request;

import com.prophius.enums.UserType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequest {

    @NotNull(message = "userName is required")
    private String userName;

    @NotNull(message = "Email is required")
    private String email;

    @NotNull(message = "Password is required")
    private String password;

    @NotNull(message = "User type is required")
    @Enumerated(EnumType.STRING)
    private UserType type;
}
