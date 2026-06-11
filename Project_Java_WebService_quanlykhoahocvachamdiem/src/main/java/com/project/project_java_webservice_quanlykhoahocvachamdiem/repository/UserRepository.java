package com.project.project_java_webservice_quanlykhoahocvachamdiem.repository;

import com.project.project_java_webservice_quanlykhoahocvachamdiem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}