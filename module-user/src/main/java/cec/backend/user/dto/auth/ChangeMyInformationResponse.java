package cec.backend.user.dto.auth;

import cec.backend.core.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeMyInformationResponse {
    private String userId;
    private String nickname;
    private String phoneNumber;
    private String profilePicture;

    public static ChangeMyInformationResponse from(User user) {
        return ChangeMyInformationResponse.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .phoneNumber(user.getPhoneNumber())
                .profilePicture(user.getProfilePicture())
                .build();
    }
} 