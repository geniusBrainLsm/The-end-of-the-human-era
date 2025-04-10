package cec.backend.user.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResetPasswordRequest {
    private String email;
    private String resetToken;
    private String newPassword;
} 