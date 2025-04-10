package cec.backend.user.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeMyInformationRequest {
    private String nickname;
    private String phoneNumber;
    private String profilePicture;
} 