package cec.backend.user.dto.equipment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BulkEquipmentRentalRequest {
    private List<String> equipmentIds;
    private LocalDateTime rentalStartTime;
    private LocalDateTime rentalEndTime;
} 