package cec.backend.core.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @Column(name = "category_id")
    private String id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Equipment> equipments = new ArrayList<>();
} 