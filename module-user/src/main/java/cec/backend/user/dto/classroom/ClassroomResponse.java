package cec.backend.user.dto.classroom;

import cec.backend.core.model.entity.Classroom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomResponse {
    private String id;
    private String name;
    private String location;
    private String status;
    private LocalDateTime rentalStartTime;
    private LocalDateTime rentalEndTime;
    private String adminId;
    private String adminName;

    public static ClassroomResponse from(Classroom classroom) {
        return ClassroomResponse.builder()
                .id(classroom.getId())
                .name(classroom.getName())
                .location(classroom.getLocation())
                .status(classroom.getStatus())
                .rentalStartTime(classroom.getRentalStartTime())
                .rentalEndTime(classroom.getRentalEndTime())
                .adminId(classroom.getAdmin() != null ? classroom.getAdmin().getId() : null)
                .adminName(classroom.getAdmin() != null ? classroom.getAdmin().getName() : null)
                .build();
    }
} 