package com.safeway.tech.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmSignUpRequest {
    private String email;
    private String confirmationCode;
}
