package cec.backend.user.dto.equipment;

import cec.backend.core.model.entity.RentalHistory;
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
public class EquipmentRentalHistoryResponse {
    private List<RentalHistoryDto> rentalHistories;
    private int totalPages;
    private long totalElements;
    private boolean hasNext;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RentalHistoryDto {
        private String id;
        private String equipmentId;
        private String equipmentName;
        private String userId;
        private String userName;
        private LocalDateTime rentalStartTime;
        private LocalDateTime rentalEndTime;
        private String status;

        public static RentalHistoryDto from(RentalHistory history) {
            return RentalHistoryDto.builder()
                    .id(history.getId())
                    .equipmentId(history.getEquipment().getId())
                    .equipmentName(history.getEquipment().getName())
                    .userId(history.getUser().getId())
                    .userName(history.getUser().getName())
                    .rentalStartTime(history.getRentalStartTime())
                    .rentalEndTime(history.getRentalEndTime())
                    .status(history.getStatus())
                    .build();
        }
    }

    public static EquipmentRentalHistoryResponse of(List<RentalHistory> histories,
                                                  int totalPages, long totalElements, boolean hasNext) {
        List<RentalHistoryDto> historyDtos = histories.stream()
                .map(RentalHistoryDto::from)
                .collect(Collectors.toList());

        return EquipmentRentalHistoryResponse.builder()
                .rentalHistories(historyDtos)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .hasNext(hasNext)
                .build();
    }
} 