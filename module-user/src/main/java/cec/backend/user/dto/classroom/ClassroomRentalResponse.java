package cec.backend.user.dto.classroom;

import cec.backend.core.model.entity.RentalHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomRentalResponse {
    private String rentalId;
    private String classroomId;
    private String classroomName;
    private String userId;
    private String userName;
    private LocalDateTime rentalStartTime;
    private LocalDateTime rentalEndTime;
    private String status;

    public static ClassroomRentalResponse from(RentalHistory rentalHistory) {
        return ClassroomRentalResponse.builder()
                .rentalId(rentalHistory.getId())
                .classroomId(rentalHistory.getClassroom().getId())
                .classroomName(rentalHistory.getClassroom().getName())
                .userId(rentalHistory.getUser().getId())
                .userName(rentalHistory.getUser().getName())
                .rentalStartTime(rentalHistory.getRentalStartTime())
                .rentalEndTime(rentalHistory.getRentalEndTime())
                .status(rentalHistory.getStatus())
                .build();
    }
} 