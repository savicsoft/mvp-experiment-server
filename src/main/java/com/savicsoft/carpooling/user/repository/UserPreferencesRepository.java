package com.savicsoft.carpooling.user.repository;

import com.savicsoft.carpooling.user.model.entity.UserPreferences;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPreferencesRepository extends JpaRepository<UserPreferences, Long> {
}
