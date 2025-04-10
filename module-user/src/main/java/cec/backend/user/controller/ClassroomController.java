package cec.backend.user.controller;

import cec.backend.user.dto.classroom.ClassroomListResponse;
import cec.backend.user.dto.classroom.ClassroomRentalCancelResponse;
import cec.backend.user.dto.classroom.ClassroomRentalRequest;
import cec.backend.user.dto.classroom.ClassroomRentalResponse;
import cec.backend.user.dto.classroom.ClassroomResponse;
import cec.backend.user.service.ClassroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/classrooms")
@RequiredArgsConstructor
public class ClassroomController {

    private final ClassroomService classroomService;

    @GetMapping
    public ResponseEntity<ClassroomListResponse> getClassroomList(
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(classroomService.getClassroomList(pageable));
    }

    @GetMapping("/{classroomId}")
    public ResponseEntity<ClassroomResponse> getClassroomDetail(
            @PathVariable Long classroomId) {
        return ResponseEntity.ok(classroomService.getClassroomDetail(classroomId));
    }

    @PostMapping("/{classroomId}/rent")
    public ResponseEntity<ClassroomRentalResponse> rentClassroom(
            @PathVariable Long classroomId,
            @RequestBody ClassroomRentalRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(classroomService.rentClassroom(classroomId, request, userDetails.getUsername()));
    }

    @PostMapping("/{classroomId}/cancel")
    public ResponseEntity<ClassroomRentalCancelResponse> cancelRental(
            @PathVariable Long classroomId,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(classroomService.cancelRental(classroomId, userDetails.getUsername()));
    }

    @GetMapping("/calendar")
    public ResponseEntity<ClassroomListResponse> getRentalCalendar(Pageable pageable) {
        return ResponseEntity.ok(classroomService.getRentalCalendar(pageable));
    }
} 