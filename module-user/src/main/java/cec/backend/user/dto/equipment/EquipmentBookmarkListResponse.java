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
public class EquipmentBookmarkListResponse {
    private List<BookmarkedEquipmentDto> bookmarkedEquipments;
    private int totalPages;
    private long totalElements;
    private boolean hasNext;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookmarkedEquipmentDto {
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

        public static BookmarkedEquipmentDto from(Equipment equipment) {
            return BookmarkedEquipmentDto.builder()
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
                    .build();
        }
    }

    public static EquipmentBookmarkListResponse of(List<Equipment> equipments,
                                                 int totalPages, long totalElements, boolean hasNext) {
        List<BookmarkedEquipmentDto> bookmarkedEquipmentDtos = equipments.stream()
                .map(BookmarkedEquipmentDto::from)
                .collect(Collectors.toList());

        return EquipmentBookmarkListResponse.builder()
                .bookmarkedEquipments(bookmarkedEquipmentDtos)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .hasNext(hasNext)
                .build();
    }
} 