package cec.backend.user.dto.equipment;

import cec.backend.core.model.entity.Equipment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentRentalResponse {
    private String equipmentId;
    private String equipmentName;
    private String userId;
    private LocalDateTime rentalStartTime;
    private LocalDateTime rentalEndTime;
    private String status;

    public static EquipmentRentalResponse from(Equipment equipment) {
        return EquipmentRentalResponse.builder()
                .equipmentId(equipment.getId())
                .equipmentName(equipment.getName())
                .userId(equipment.getRentedByUser().getId())
                .rentalStartTime(equipment.getRentalStartTime())
                .rentalEndTime(equipment.getRentalEndTime())
                .status(equipment.getStatus())
                .build();
    }
} 