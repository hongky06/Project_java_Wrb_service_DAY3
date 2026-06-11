package com.project.project_java_webservice_quanlykhoahocvachamdiem.controller;

import com.project.project_java_webservice_quanlykhoahocvachamdiem.dto.request.GradeRequest;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.dto.request.SubmitRequest;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.dto.response.SubmissionResponse;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.service.SubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    @PostMapping("/api/v1/student/submissions")
    public ResponseEntity<SubmissionResponse> submit(@Valid @RequestBody SubmitRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(submissionService.submitAssignment(request));
    }

    @PutMapping("/api/v1/lecturer/submissions/{id}/grade")
    public ResponseEntity<SubmissionResponse> grade(
            @PathVariable Long id,
            @Valid @RequestBody GradeRequest request) {
        return ResponseEntity.ok(submissionService.gradeSubmission(id, request.getScore(), request.getFeedback()));
    }

    @GetMapping("/api/v1/lecturer/submissions/{id}")
    public ResponseEntity<SubmissionResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(submissionService.getById(id));
    }
}