package cec.backend.core.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "classrooms")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Classroom {
    @Id
    @Column(name = "classroom_id")
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private User admin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rented_by_user_id")
    private User rentedByUser;

    private LocalDateTime rentalStartTime;
    private LocalDateTime rentalEndTime;

    @OneToMany(mappedBy = "classroom")
    private List<RentalHistory> rentalHistories = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public boolean isAvailable() {
        return "AVAILABLE".equals(status);
    }

    public void setAvailable(boolean available) {
        this.status = available ? "AVAILABLE" : "RENTED";
    }

    public void setRentedByUser(User user) {
        this.rentedByUser = user;
    }

    public void setRentalStartTime(LocalDateTime rentalStartTime) {
        this.rentalStartTime = rentalStartTime;
    }

    public void setRentalEndTime(LocalDateTime rentalEndTime) {
        this.rentalEndTime = rentalEndTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }
} 