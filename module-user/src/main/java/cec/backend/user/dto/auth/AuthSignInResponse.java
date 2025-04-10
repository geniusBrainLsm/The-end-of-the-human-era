package cec.backend.user.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthSignInResponse {
    private String accessToken;
    private String refreshToken;
    private String userId;
    private String role;
} 