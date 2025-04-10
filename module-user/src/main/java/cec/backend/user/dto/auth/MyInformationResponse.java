package cec.backend.user.dto.auth;

import cec.backend.core.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyInformationResponse {
    private String userId;
    private String name;
    private String nickname;
    private String department;
    private String major;
    private String year;
    private String gender;
    private String professor;
    private String phoneNumber;
    private String email;
    private String profilePicture;
    private LocalDate birthDate;
    private String role;

    public static MyInformationResponse from(User user) {
        return MyInformationResponse.builder()
                .userId(user.getId())
                .name(user.getName())
                .nickname(user.getNickname())
                .department(user.getDepartment())
                .major(user.getMajor())
                .year(user.getYear())
                .gender(user.getGender())
                .professor(user.getProfessor())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .profilePicture(user.getProfilePicture())
                .birthDate(user.getBirthDate())
                .role(user.getRole())
                .build();
    }
} 