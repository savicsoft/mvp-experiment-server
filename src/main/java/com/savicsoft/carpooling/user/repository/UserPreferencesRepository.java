package com.savicsoft.carpooling.user.repository;

import com.savicsoft.carpooling.user.model.entity.UserPreferences;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserPreferencesRepository extends JpaRepository<UserPreferences, Long> {

    Optional<UserPreferences> findById(UUID id);
}
