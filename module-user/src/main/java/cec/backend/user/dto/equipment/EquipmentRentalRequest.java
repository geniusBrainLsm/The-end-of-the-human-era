package cec.backend.user.dto.equipment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentRentalRequest {
    private LocalDateTime rentalStartTime;
    private LocalDateTime rentalEndTime;
} 