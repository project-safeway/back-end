package com.safeway.tech.dto;

public record ConfirmSignUpRequest(
    String email,
    String confirmationCode
) {
}
