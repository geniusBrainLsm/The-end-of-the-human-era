package cec.backend.user.dto.classroom;

import cec.backend.core.model.entity.Classroom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomListResponse {
    private List<ClassroomDto> classrooms;
    private int totalPages;
    private long totalElements;
    private boolean hasNext;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClassroomDto {
        private String id;
        private String name;
        private String location;
        private String status;
        private String rentalStartTime;
        private String rentalEndTime;
        private String adminId;

        public static ClassroomDto from(Classroom classroom) {
            return ClassroomDto.builder()
                .id(classroom.getId())
                .name(classroom.getName())
                .location(classroom.getLocation())
                .status(classroom.getStatus())
                .rentalStartTime(classroom.getRentalStartTime() != null ? classroom.getRentalStartTime().toString() : null)
                .rentalEndTime(classroom.getRentalEndTime() != null ? classroom.getRentalEndTime().toString() : null)
                .adminId(classroom.getAdmin() != null ? classroom.getAdmin().getId() : null)
                .build();
        }
    }

    public static ClassroomListResponse of(List<Classroom> classrooms, int totalPages, long totalElements, boolean hasNext) {
        return ClassroomListResponse.builder()
            .classrooms(classrooms.stream()
                .map(ClassroomDto::from)
                .collect(Collectors.toList()))
            .totalPages(totalPages)
            .totalElements(totalElements)
            .hasNext(hasNext)
            .build();
    }
} 