package cec.backend.user.dto.classroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomRentalCancelResponse {
    private String rentalId;
    private String message;
    private boolean success;
} 