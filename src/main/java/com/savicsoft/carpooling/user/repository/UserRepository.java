package com.savicsoft.carpooling.user.repository;

import com.savicsoft.carpooling.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(UUID id);
    void deleteById(UUID id);
    Optional<User> getUserByEmail(String email);
    Boolean existsByEmail(String email);
    boolean existsById(UUID id);


}
