package cec.backend.user.dto.classroom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomRentalRequest {
    private LocalDateTime rentalStartTime;
    private LocalDateTime rentalEndTime;
} 