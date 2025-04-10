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
public class BulkEquipmentRentalResponse {
    private List<RentalResultDto> results;
    private int successCount;
    private int failCount;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RentalResultDto {
        private String equipmentId;
        private String equipmentName;
        private boolean success;
        private String message;
        private LocalDateTime rentalStartTime;
        private LocalDateTime rentalEndTime;
        private String status;

        public static RentalResultDto success(Equipment equipment) {
            return RentalResultDto.builder()
                    .equipmentId(equipment.getId())
                    .equipmentName(equipment.getName())
                    .success(true)
                    .message("대여 신청이 완료되었습니다.")
                    .rentalStartTime(equipment.getRentalStartTime())
                    .rentalEndTime(equipment.getRentalEndTime())
                    .status(equipment.getStatus())
                    .build();
        }

        public static RentalResultDto fail(String equipmentId, String message) {
            return RentalResultDto.builder()
                    .equipmentId(equipmentId)
                    .success(false)
                    .message(message)
                    .build();
        }
    }

    public static BulkEquipmentRentalResponse of(List<RentalResultDto> results) {
        long successCount = results.stream().filter(RentalResultDto::isSuccess).count();
        return BulkEquipmentRentalResponse.builder()
                .results(results)
                .successCount((int) successCount)
                .failCount(results.size() - (int) successCount)
                .build();
    }
} 