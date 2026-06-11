package com.project.project_java_webservice_quanlykhoahocvachamdiem.repository;

import com.project.project_java_webservice_quanlykhoahocvachamdiem.entity.TokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, Long> {
    boolean existsByToken(String token);
}