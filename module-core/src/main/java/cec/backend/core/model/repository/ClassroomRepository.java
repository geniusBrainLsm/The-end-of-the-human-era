package cec.backend.core.model.repository;

import cec.backend.core.model.entity.Classroom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, String> {
    Page<Classroom> findByNameContainingIgnoreCase(String name, Pageable pageable);
    
    Page<Classroom> findByLocationContainingIgnoreCase(String location, Pageable pageable);
    
    @Query("SELECT c FROM Classroom c WHERE c.status = :status")
    Page<Classroom> findByStatus(@Param("status") String status, Pageable pageable);
    
    @Query("SELECT c FROM Classroom c WHERE c.rentalStartTime <= :endTime AND c.rentalEndTime >= :startTime")
    List<Classroom> findOverlappingRentals(@Param("startTime") LocalDateTime startTime, 
                                         @Param("endTime") LocalDateTime endTime);
    
    @Query("SELECT c FROM Classroom c WHERE c.admin.id = :adminId")
    Page<Classroom> findByAdminId(@Param("adminId") String adminId, Pageable pageable);
} 