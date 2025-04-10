package cec.backend.user.service;

import cec.backend.core.model.entity.Classroom;
import cec.backend.core.model.entity.RentalHistory;
import cec.backend.core.model.entity.User;
import cec.backend.core.model.repository.ClassroomRepository;
import cec.backend.core.model.repository.RentalHistoryRepository;
import cec.backend.core.model.repository.UserRepository;
import cec.backend.user.dto.classroom.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final UserRepository userRepository;
    private final RentalHistoryRepository rentalHistoryRepository;

    @Transactional(readOnly = true)
    public ClassroomListResponse getClassroomList(Pageable pageable) {
        Page<Classroom> classrooms = classroomRepository.findAll(pageable);
        return ClassroomListResponse.of(
            classrooms.getContent(),
            classrooms.getTotalPages(),
            classrooms.getTotalElements(),
            classrooms.hasNext()
        );
    }

    @Transactional(readOnly = true)
    public ClassroomResponse getClassroomDetail(String classroomId) {
        Classroom classroom = classroomRepository.findById(classroomId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강의실입니다."));
        return ClassroomResponse.from(classroom);
    }

    @Transactional
    public ClassroomRentalResponse rentClassroom(String classroomId, ClassroomRentalRequest request, String username) {
        Classroom classroom = classroomRepository.findById(classroomId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강의실입니다."));
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (!classroom.isAvailable()) {
            throw new IllegalStateException("이미 대여 중인 강의실입니다.");
        }

        LocalDateTime now = LocalDateTime.now();
        if (request.getRentalStartTime().isBefore(now)) {
            throw new IllegalArgumentException("대여 시작 시간은 현재 시간 이후여야 합니다.");
        }

        if (request.getRentalEndTime().isBefore(request.getRentalStartTime())) {
            throw new IllegalArgumentException("대여 종료 시간은 시작 시간 이후여야 합니다.");
        }

        classroom.setAvailable(false);
        classroom.setRentedByUser(user);
        classroom.setRentalStartTime(request.getRentalStartTime());
        classroom.setRentalEndTime(request.getRentalEndTime());
        classroom.setStatus("RENTED");

        RentalHistory rentalHistory = RentalHistory.builder()
            .classroom(classroom)
            .user(user)
            .rentalStartTime(request.getRentalStartTime())
            .rentalEndTime(request.getRentalEndTime())
            .status("RENTED")
            .build();

        rentalHistoryRepository.save(rentalHistory);
        classroomRepository.save(classroom);

        return ClassroomRentalResponse.from(rentalHistory);
    }

    @Transactional
    public ClassroomRentalCancelResponse cancelRental(String classroomId, String username) {
        Classroom classroom = classroomRepository.findById(classroomId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강의실입니다."));
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (classroom.isAvailable()) {
            throw new IllegalStateException("이미 대여 가능한 상태입니다.");
        }

        if (!classroom.getRentedByUser().equals(user)) {
            throw new IllegalStateException("대여 취소 권한이 없습니다.");
        }

        RentalHistory rentalHistory = rentalHistoryRepository.findByClassroomAndUserAndStatus(
            classroom, user, "RENTED")
            .orElseThrow(() -> new IllegalArgumentException("대여 내역을 찾을 수 없습니다."));

        classroom.setAvailable(true);
        classroom.setRentedByUser(null);
        classroom.setRentalStartTime(null);
        classroom.setRentalEndTime(null);
        classroom.setStatus("AVAILABLE");

        rentalHistory.setStatus("CANCELLED");
        rentalHistory.setCancelledAt(LocalDateTime.now());

        rentalHistoryRepository.save(rentalHistory);
        classroomRepository.save(classroom);

        return ClassroomRentalCancelResponse.builder()
            .rentalId(rentalHistory.getId())
            .message("대여가 성공적으로 취소되었습니다.")
            .success(true)
            .build();
    }

    public ClassroomListResponse getRentalCalendar(Pageable pageable) {
        Page<RentalHistory> rentalHistories = rentalHistoryRepository.findAll(pageable);
        
        return ClassroomListResponse.of(
                rentalHistories.getContent().stream()
                    .map(RentalHistory::getClassroom)
                    .collect(Collectors.toList()),
                rentalHistories.getTotalPages(),
                rentalHistories.getTotalElements(),
                rentalHistories.hasNext()
        );
    }
} 