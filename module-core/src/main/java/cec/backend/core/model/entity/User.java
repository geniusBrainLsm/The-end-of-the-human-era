package cec.backend.core.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {
    @Id
    @Column(name = "user_id")
    private String id;

    @Column(nullable = false)
    private String name;

    private String nickname;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String major;

    @Column(nullable = false)
    private String year;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String professor;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String profilePicture;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private int restrictionCount;

    @Column(nullable = false)
    private int reportCount;

    @Column(nullable = false)
    private String role;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user")
    private List<Equipment> equipments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Complaint> complaints = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<RentalHistory> rentalHistories = new ArrayList<>();

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void changeProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void changeEmail(String email) {
        this.email = email;
    }
} 