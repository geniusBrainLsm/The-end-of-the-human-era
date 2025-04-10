package cec.backend.user.dto.classroom;

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
public class ClassroomRentalCalendarResponse {
    private List<RentalScheduleDto> schedules;
    private int totalPages;
    private long totalElements;
    private boolean hasNext;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RentalScheduleDto {
        private String rentalId;
        private String classroomId;
        private String classroomName;
        private String userId;
        private String userName;
        private LocalDateTime rentalStartTime;
        private LocalDateTime rentalEndTime;
        private String status;

        public static RentalScheduleDto from(RentalHistory rentalHistory) {
            return RentalScheduleDto.builder()
                    .rentalId(rentalHistory.getId())
                    .classroomId(rentalHistory.getClassroom().getId())
                    .classroomName(rentalHistory.getClassroom().getName())
                    .userId(rentalHistory.getUser().getId())
                    .userName(rentalHistory.getUser().getName())
                    .rentalStartTime(rentalHistory.getRentalStartTime())
                    .rentalEndTime(rentalHistory.getRentalEndTime())
                    .status(rentalHistory.getStatus())
                    .build();
        }
    }

    public static ClassroomRentalCalendarResponse of(List<RentalHistory> rentalHistories,
                                                   int totalPages, long totalElements, boolean hasNext) {
        List<RentalScheduleDto> scheduleDtos = rentalHistories.stream()
                .map(RentalScheduleDto::from)
                .collect(Collectors.toList());

        return ClassroomRentalCalendarResponse.builder()
                .schedules(scheduleDtos)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .hasNext(hasNext)
                .build();
    }
} 