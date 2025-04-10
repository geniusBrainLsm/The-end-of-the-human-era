package cec.backend.core.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "equipment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Equipment {
    @Id
    @Column(name = "equipment_id")
    private String id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private String assetNumber;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String description;

    private String relatedItems;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private boolean available;

    private LocalDateTime rentalStartTime;

    private LocalDateTime rentalEndTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rented_by_user_id")
    private User rentedByUser;

    @OneToMany(mappedBy = "equipment")
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "equipment")
    private List<RentalHistory> rentalHistories = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setRentedByUser(User rentedByUser) {
        this.rentedByUser = rentedByUser;
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