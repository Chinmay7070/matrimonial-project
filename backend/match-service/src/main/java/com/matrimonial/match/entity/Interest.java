package com.matrimonial.match.entity;

import com.matrimonial.match.entity.Enums.InterestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "interests",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "sender_user_id",
                                "receiver_user_id"
                        }
                )
        }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_user_id",nullable = false)
    private Long sernderUserId;

    @Column(name = "receiver_user_id", nullable = false)
    private Long receiverUserId;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private InterestStatus status = InterestStatus.PENDING;

    @Column(name = "sent_at", nullable = false, updatable = false)
    private LocalDateTime sentAt;

    @Column(name = "responded_at")
    private LocalDateTime respondedAt;

    @PrePersist
    public void beforeSave() {
        this.sentAt = LocalDateTime.now();
    }
}
