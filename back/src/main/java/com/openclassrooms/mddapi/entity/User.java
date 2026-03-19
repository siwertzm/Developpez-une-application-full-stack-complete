package com.openclassrooms.mddapi.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    // Username (pseudo)
    @NotBlank
    @Column(nullable = false, unique = true)
    private String username;

    // Email
    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    // Mot de passe (hashé plus tard avec BCrypt)
    @NotBlank
    @Column(nullable = false)
    private String password;

    // Date de création
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Abonnements aux thèmes
    @ManyToMany
    @JoinTable(
        name = "subscriptions",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "topic_id")
    )
    private Set<Topic> subscriptions = new HashSet<>();

    // Initialisation automatique de la date
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}