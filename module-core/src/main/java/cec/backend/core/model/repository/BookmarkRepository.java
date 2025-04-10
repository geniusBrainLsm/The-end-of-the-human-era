package cec.backend.core.model.repository;

import cec.backend.core.model.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, String> {
    Optional<Bookmark> findByUserIdAndEquipmentId(String userId, String equipmentId);

    @Query("SELECT b.equipment.id FROM Bookmark b WHERE b.user.id = :userId")
    List<String> findBookmarkedEquipmentIdsByUserId(@Param("userId") String userId);

    void deleteByUserIdAndEquipmentId(String userId, String equipmentId);
} 