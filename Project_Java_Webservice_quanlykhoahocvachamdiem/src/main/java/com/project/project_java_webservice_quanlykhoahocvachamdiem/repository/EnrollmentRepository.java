package com.project.project_java_webservice_quanlykhoahocvachamdiem.repository;

import com.project.project_java_webservice_quanlykhoahocvachamdiem.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    boolean existsByUserIdAndCourseId(Long userId, Long courseId);
    List<Enrollment> findByUserId(Long userId);
}