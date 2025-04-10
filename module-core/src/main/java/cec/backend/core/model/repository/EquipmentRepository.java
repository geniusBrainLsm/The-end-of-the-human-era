package cec.backend.core.model.repository;

import cec.backend.core.model.entity.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, String> {
    Page<Equipment> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Equipment> findByCategoryId(String categoryId, Pageable pageable);

    @Query("SELECT e FROM Equipment e WHERE e.id IN :equipmentIds")
    List<Equipment> findByIds(@Param("equipmentIds") List<String> equipmentIds);

    @Query("SELECT e FROM Equipment e WHERE e.available = true AND " +
            "(e.rentalStartTime IS NULL OR e.rentalEndTime < :now)")
    Page<Equipment> findAvailable(@Param("now") LocalDateTime now, Pageable pageable);

    @Query("SELECT e FROM Equipment e WHERE e.id IN " +
            "(SELECT b.equipment.id FROM Bookmark b WHERE b.user.id = :userId)")
    Page<Equipment> findBookmarkedByUserId(@Param("userId") String userId, Pageable pageable);
} 