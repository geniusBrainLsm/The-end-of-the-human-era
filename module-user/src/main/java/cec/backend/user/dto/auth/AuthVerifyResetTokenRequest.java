package cec.backend.user.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthVerifyResetTokenRequest {
    private String email;
    private String resetToken;
} 