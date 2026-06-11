package com.project.project_java_webservice_quanlykhoahocvachamdiem.repository;

import com.project.project_java_webservice_quanlykhoahocvachamdiem.entity.RefreshToken;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUser(User user);
}