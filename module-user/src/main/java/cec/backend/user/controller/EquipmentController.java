package cec.backend.user.controller;

import cec.backend.user.dto.equipment.*;
import cec.backend.user.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/equipment")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentService equipmentService;

    @GetMapping
    public ResponseEntity<EquipmentListResponse> getEquipments(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String categoryId,
            @RequestParam(required = false, defaultValue = "false") boolean onlyAvailable,
            @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(equipmentService.getEquipments(name, categoryId, onlyAvailable, pageable));
    }

    @GetMapping("/{equipmentId}")
    public ResponseEntity<EquipmentResponse> getEquipment(@PathVariable String equipmentId) {
        return ResponseEntity.ok(equipmentService.getEquipment(equipmentId));
    }

    @PostMapping("/{equipmentId}/rental")
    public ResponseEntity<EquipmentRentalResponse> rentEquipment(
            @PathVariable String equipmentId,
            @RequestBody EquipmentRentalRequest request) {
        return ResponseEntity.ok(equipmentService.rentEquipment(equipmentId, request));
    }

    @GetMapping("/{equipmentId}/favorite")
    public ResponseEntity<EquipmentFavoriteToggleResponse> toggleFavorite(@PathVariable String equipmentId) {
        return ResponseEntity.ok(equipmentService.toggleFavorite(equipmentId));
    }

    @GetMapping("/favorites")
    public ResponseEntity<EquipmentBookmarkListResponse> getFavorites(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(equipmentService.getFavorites(pageable));
    }

    @GetMapping("/{equipmentId}/rental-history")
    public ResponseEntity<EquipmentRentalHistoryResponse> getRentalHistory(
            @PathVariable String equipmentId,
            @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(equipmentService.getRentalHistory(equipmentId, pageable));
    }

    @PostMapping("/bulk-rental")
    public ResponseEntity<BulkEquipmentRentalResponse> bulkRental(@RequestBody BulkEquipmentRentalRequest request) {
        return ResponseEntity.ok(equipmentService.bulkRental(request));
    }
} 