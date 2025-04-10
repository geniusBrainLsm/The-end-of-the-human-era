package cec.backend.user.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailChangeVerifyRequest {
    private String newEmail;
    private String verificationToken;
} 