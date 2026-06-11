package com.project.project_java_webservice_quanlykhoahocvachamdiem.service;

import com.project.project_java_webservice_quanlykhoahocvachamdiem.dto.request.EnrollCourseRequest;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.dto.response.EnrollmentResponse;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.entity.Course;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.entity.Enrollment;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.entity.User;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.repository.CourseRepository;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.repository.EnrollmentRepository;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public EnrollmentResponse enroll(EnrollCourseRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found: " + request.getUserId()));
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found: " + request.getCourseId()));

        if (enrollmentRepository.existsByUserIdAndCourseId(request.getUserId(), request.getCourseId()))
            throw new RuntimeException("Sinh viên đã đăng ký khóa học này rồi");

        Enrollment enrollment = Enrollment.builder()
                .user(user).course(course).build();
        enrollmentRepository.save(enrollment);
        return toResponse(enrollment);
    }

    public List<EnrollmentResponse> getByUserId(Long userId) {
        return enrollmentRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private EnrollmentResponse toResponse(Enrollment e) {
        return EnrollmentResponse.builder()
                .id(e.getId())
                .userId(e.getUser().getId())
                .courseId(e.getCourse().getId())
                .courseName(e.getCourse().getName())
                .build();
    }
}