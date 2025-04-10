package cec.backend.core.model.repository;

import cec.backend.core.model.entity.RentalHistory;
import cec.backend.core.model.entity.Classroom;
import cec.backend.core.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RentalHistoryRepository extends JpaRepository<RentalHistory, String> {
    Page<RentalHistory> findByEquipmentId(String equipmentId, Pageable pageable);
    Page<RentalHistory> findByUserId(String userId, Pageable pageable);
    Optional<RentalHistory> findByClassroomAndUserAndStatus(Classroom classroom, User user, String status);
} 