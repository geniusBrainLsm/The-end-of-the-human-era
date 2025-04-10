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
public class EquipmentResponse {
    private String id;
    private String name;
    private String category;
    private String assetNumber;
    private String model;
    private String description;
    private String relatedItems;
    private String status;
    private boolean available;
    private LocalDateTime rentalStartTime;
    private LocalDateTime rentalEndTime;
    private String rentedByUserId;
    private boolean isBookmarked;

    public static EquipmentResponse from(Equipment equipment, boolean isBookmarked) {
        return EquipmentResponse.builder()
                .id(equipment.getId())
                .name(equipment.getName())
                .category(equipment.getCategory().getName())
                .assetNumber(equipment.getAssetNumber())
                .model(equipment.getModel())
                .description(equipment.getDescription())
                .relatedItems(equipment.getRelatedItems())
                .status(equipment.getStatus())
                .available(equipment.isAvailable())
                .rentalStartTime(equipment.getRentalStartTime())
                .rentalEndTime(equipment.getRentalEndTime())
                .rentedByUserId(equipment.getRentedByUser() != null ? equipment.getRentedByUser().getId() : null)
                .isBookmarked(isBookmarked)
                .build();
    }
} 