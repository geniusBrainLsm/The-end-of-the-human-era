package cec.backend.user.service;

import cec.backend.core.model.entity.Bookmark;
import cec.backend.core.model.entity.Equipment;
import cec.backend.core.model.entity.RentalHistory;
import cec.backend.core.model.entity.User;
import cec.backend.core.model.repository.BookmarkRepository;
import cec.backend.core.model.repository.EquipmentRepository;
import cec.backend.core.model.repository.RentalHistoryRepository;
import cec.backend.user.dto.equipment.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final BookmarkRepository bookmarkRepository;
    private final RentalHistoryRepository rentalHistoryRepository;
    private final UserService userService;

    public EquipmentListResponse getEquipments(String name, String categoryId, boolean onlyAvailable, Pageable pageable) {
        Page<Equipment> equipmentPage;
        if (name != null && !name.isEmpty()) {
            equipmentPage = equipmentRepository.findByNameContainingIgnoreCase(name, pageable);
        } else if (categoryId != null && !categoryId.isEmpty()) {
            equipmentPage = equipmentRepository.findByCategoryId(categoryId, pageable);
        } else if (onlyAvailable) {
            equipmentPage = equipmentRepository.findAvailable(LocalDateTime.now(), pageable);
        } else {
            equipmentPage = equipmentRepository.findAll(pageable);
        }

        String userId = userService.getCurrentUserId();
        List<String> bookmarkedEquipmentIds = bookmarkRepository.findBookmarkedEquipmentIdsByUserId(userId);

        return EquipmentListResponse.of(
                equipmentPage.getContent(),
                bookmarkedEquipmentIds,
                equipmentPage.getTotalPages(),
                equipmentPage.getTotalElements(),
                equipmentPage.hasNext()
        );
    }

    public EquipmentResponse getEquipment(String equipmentId) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new IllegalArgumentException("Equipment not found"));

        String userId = userService.getCurrentUserId();
        boolean isBookmarked = bookmarkRepository.findByUserIdAndEquipmentId(userId, equipmentId).isPresent();

        return EquipmentResponse.from(equipment, isBookmarked);
    }

    @Transactional
    public EquipmentRentalResponse rentEquipment(String equipmentId, EquipmentRentalRequest request) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new IllegalArgumentException("Equipment not found"));

        if (!equipment.isAvailable()) {
            throw new IllegalStateException("Equipment is not available");
        }

        String userId = userService.getCurrentUserId();
        User user = userService.getUser(userId);

        equipment.setAvailable(false);
        equipment.setRentedByUser(user);
        equipment.setRentalStartTime(request.getRentalStartTime());
        equipment.setRentalEndTime(request.getRentalEndTime());
        equipment.setStatus("RENTED");

        RentalHistory rentalHistory = RentalHistory.builder()
                .id(UUID.randomUUID().toString())
                .equipment(equipment)
                .user(user)
                .rentalStartTime(request.getRentalStartTime())
                .rentalEndTime(request.getRentalEndTime())
                .status("RENTED")
                .build();

        rentalHistoryRepository.save(rentalHistory);

        return EquipmentRentalResponse.from(equipment);
    }

    @Transactional
    public EquipmentFavoriteToggleResponse toggleFavorite(String equipmentId) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new IllegalArgumentException("Equipment not found"));

        String userId = userService.getCurrentUserId();
        boolean isBookmarked = bookmarkRepository.findByUserIdAndEquipmentId(userId, equipmentId).isPresent();

        if (isBookmarked) {
            bookmarkRepository.deleteByUserIdAndEquipmentId(userId, equipmentId);
            return EquipmentFavoriteToggleResponse.builder()
                    .equipmentId(equipmentId)
                    .isBookmarked(false)
                    .message("즐겨찾기가 해제되었습니다.")
                    .build();
        } else {
            User user = userService.getUser(userId);
            Bookmark bookmark = Bookmark.builder()
                    .id(UUID.randomUUID().toString())
                    .user(user)
                    .equipment(equipment)
                    .build();
            bookmarkRepository.save(bookmark);
            return EquipmentFavoriteToggleResponse.builder()
                    .equipmentId(equipmentId)
                    .isBookmarked(true)
                    .message("즐겨찾기에 추가되었습니다.")
                    .build();
        }
    }

    public EquipmentBookmarkListResponse getFavorites(Pageable pageable) {
        String userId = userService.getCurrentUserId();
        Page<Equipment> bookmarkedEquipments = equipmentRepository.findBookmarkedByUserId(userId, pageable);

        return EquipmentBookmarkListResponse.of(
                bookmarkedEquipments.getContent(),
                bookmarkedEquipments.getTotalPages(),
                bookmarkedEquipments.getTotalElements(),
                bookmarkedEquipments.hasNext()
        );
    }

    public EquipmentRentalHistoryResponse getRentalHistory(String equipmentId, Pageable pageable) {
        Page<RentalHistory> rentalHistories = rentalHistoryRepository.findByEquipmentId(equipmentId, pageable);

        return EquipmentRentalHistoryResponse.of(
                rentalHistories.getContent(),
                rentalHistories.getTotalPages(),
                rentalHistories.getTotalElements(),
                rentalHistories.hasNext()
        );
    }

    @Transactional
    public BulkEquipmentRentalResponse bulkRental(BulkEquipmentRentalRequest request) {
        List<Equipment> equipments = equipmentRepository.findByIds(request.getEquipmentIds());
        String userId = userService.getCurrentUserId();
        User user = userService.getUser(userId);

        List<BulkEquipmentRentalResponse.RentalResultDto> results = new ArrayList<>();

        for (Equipment equipment : equipments) {
            try {
                if (!equipment.isAvailable()) {
                    results.add(BulkEquipmentRentalResponse.RentalResultDto.fail(
                            equipment.getId(), "장비가 대여 불가능한 상태입니다."));
                    continue;
                }

                equipment.setAvailable(false);
                equipment.setRentedByUser(user);
                equipment.setRentalStartTime(request.getRentalStartTime());
                equipment.setRentalEndTime(request.getRentalEndTime());
                equipment.setStatus("RENTED");

                RentalHistory rentalHistory = RentalHistory.builder()
                        .id(UUID.randomUUID().toString())
                        .equipment(equipment)
                        .user(user)
                        .rentalStartTime(request.getRentalStartTime())
                        .rentalEndTime(request.getRentalEndTime())
                        .status("RENTED")
                        .build();

                rentalHistoryRepository.save(rentalHistory);
                results.add(BulkEquipmentRentalResponse.RentalResultDto.success(equipment));
            } catch (Exception e) {
                results.add(BulkEquipmentRentalResponse.RentalResultDto.fail(
                        equipment.getId(), "대여 처리 중 오류가 발생했습니다."));
            }
        }

        return BulkEquipmentRentalResponse.of(results);
    }
} 