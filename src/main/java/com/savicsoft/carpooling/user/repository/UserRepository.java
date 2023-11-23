package com.savicsoft.carpooling.user.repository;

import com.savicsoft.carpooling.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(UUID id);
    Boolean existsByEmail(String email);
    Boolean existsById(UUID id);

}
