package com.project.project_java_webservice_quanlykhoahocvachamdiem.repository;

import com.project.project_java_webservice_quanlykhoahocvachamdiem.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByCourseId(Long courseId);
}