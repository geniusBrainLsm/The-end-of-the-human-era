package cec.backend.user.dto.equipment;

import cec.backend.core.model.entity.Equipment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentListResponse {
    private List<EquipmentDto> equipments;
    private int totalPages;
    private long totalElements;
    private boolean hasNext;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EquipmentDto {
        private String id;
        private String name;
        private String category;
        private String assetNumber;
        private String model;
        private String description;
        private String status;
        private boolean available;
        private LocalDateTime rentalStartTime;
        private LocalDateTime rentalEndTime;
        private String rentedByUserId;
        private boolean isBookmarked;

        public static EquipmentDto from(Equipment equipment, boolean isBookmarked) {
            return EquipmentDto.builder()
                    .id(equipment.getId())
                    .name(equipment.getName())
                    .category(equipment.getCategory().getName())
                    .assetNumber(equipment.getAssetNumber())
                    .model(equipment.getModel())
                    .description(equipment.getDescription())
                    .status(equipment.getStatus())
                    .available(equipment.isAvailable())
                    .rentalStartTime(equipment.getRentalStartTime())
                    .rentalEndTime(equipment.getRentalEndTime())
                    .rentedByUserId(equipment.getRentedByUser() != null ? equipment.getRentedByUser().getId() : null)
                    .isBookmarked(isBookmarked)
                    .build();
        }
    }

    public static EquipmentListResponse of(List<Equipment> equipments, List<String> bookmarkedEquipmentIds,
                                         int totalPages, long totalElements, boolean hasNext) {
        List<EquipmentDto> equipmentDtos = equipments.stream()
                .map(equipment -> EquipmentDto.from(equipment,
                        bookmarkedEquipmentIds.contains(equipment.getId())))
                .collect(Collectors.toList());

        return EquipmentListResponse.builder()
                .equipments(equipmentDtos)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .hasNext(hasNext)
                .build();
    }
}