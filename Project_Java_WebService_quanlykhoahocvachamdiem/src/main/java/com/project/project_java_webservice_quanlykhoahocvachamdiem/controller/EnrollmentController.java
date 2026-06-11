package com.project.project_java_webservice_quanlykhoahocvachamdiem.controller;

import com.project.project_java_webservice_quanlykhoahocvachamdiem.dto.request.EnrollCourseRequest;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.dto.response.EnrollmentResponse;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<EnrollmentResponse> enroll(@Valid @RequestBody EnrollCourseRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enrollmentService.enroll(request));
    }

    @GetMapping("/my/{userId}")
    public ResponseEntity<List<EnrollmentResponse>> getMyEnrollments(@PathVariable Long userId) {
        return ResponseEntity.ok(enrollmentService.getByUserId(userId));
    }
}