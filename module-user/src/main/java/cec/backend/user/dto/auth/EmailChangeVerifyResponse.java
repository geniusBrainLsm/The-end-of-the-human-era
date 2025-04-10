package cec.backend.user.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailChangeVerifyResponse {
    private boolean success;
    private String message;
    private String newEmail;
} 