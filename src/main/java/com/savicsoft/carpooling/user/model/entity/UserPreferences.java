package com.savicsoft.carpooling.user.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user_preferences")
public class UserPreferences {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "language")
    private String language;

    @Column(name = "music")
    private String music;

    @Column(name = "smoking")
    private String smoking;

    @Column(name = "communication")
    private String communication;

}
