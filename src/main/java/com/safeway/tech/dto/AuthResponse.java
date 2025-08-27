package com.safeway.tech.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {
    private String idToken;
    private String accessToken;
    private String refreshToken;
    private Integer expiresIn;
}