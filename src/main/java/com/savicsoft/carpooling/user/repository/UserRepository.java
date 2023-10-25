package com.savicsoft.carpooling.user.repository;

import com.savicsoft.carpooling.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
