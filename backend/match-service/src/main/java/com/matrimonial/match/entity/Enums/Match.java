package com.matrimonial.match.entity.Enums;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "matches",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "user_id_one",
                                "user_id_two"
                        }
                )
        }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id_one", nullable = false)
    private Long userIdOne;

    @Column(name = "user_id_two", nullable = false)
    private Long userIdTwo;

    @Column(name = "matched_at", nullable = false, updatable = false)
    private LocalDateTime matchedAt;

    @Builder.Default
    @Column(name = "is_active")
    private Boolean isActive = true;

    @PrePersist
    public void beforeSave() {
        this.matchedAt = LocalDateTime.now();
    }
}
