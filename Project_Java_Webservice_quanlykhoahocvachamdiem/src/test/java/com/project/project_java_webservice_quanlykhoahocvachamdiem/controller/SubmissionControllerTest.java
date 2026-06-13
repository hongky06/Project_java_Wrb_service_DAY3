package com.project.project_java_webservice_quanlykhoahocvachamdiem.controller;

import com.project.project_java_webservice_quanlykhoahocvachamdiem.dto.request.GradeRequest;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.dto.request.SubmitRequest;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.dto.response.SubmissionResponse;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.service.SubmissionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SubmissionControllerTest {

    @Mock
    private SubmissionService submissionService;

    @InjectMocks
    private SubmissionController submissionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSubmit_ControllerSuccess() {
        SubmitRequest request = new SubmitRequest();
        request.setUserId(1L);
        request.setCourseId(10L);
        request.setGithubLink("https://github.com/dohon/project-java");

        SubmissionResponse mockResponse = SubmissionResponse.builder()
                .id(1L)
                .userId(1L)
                .courseId(10L)
                .githubLink("https://github.com/dohon/project-java")
                .status("SUBMITTED")
                .build();

        when(submissionService.submitAssignment(any(SubmitRequest.class))).thenReturn(mockResponse);

        ResponseEntity<SubmissionResponse> response = submissionController.submit(request);

        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void testGrade_ControllerSuccess() {
        GradeRequest gradeRequest = new GradeRequest();
        gradeRequest.setScore(10.0);
        gradeRequest.setFeedback("Excellent");

        SubmissionResponse mockResponse = SubmissionResponse.builder()
                .id(1L)
                .score(10.0)
                .feedback("Excellent")
                .status("GRADED")
                .build();

        when(submissionService.gradeSubmission(1L, 10.0, "Excellent")).thenReturn(mockResponse);

        ResponseEntity<SubmissionResponse> response = submissionController.grade(1L, gradeRequest);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(10.0, response.getBody().getScore());
    }

    @Test
    void testSubmit_VerifyServiceCall() {
        SubmitRequest request = new SubmitRequest();
        request.setUserId(1L);
        request.setCourseId(10L);

        SubmissionResponse mockResponse = SubmissionResponse.builder().build();
        when(submissionService.submitAssignment(any(SubmitRequest.class))).thenReturn(mockResponse);

        submissionController.submit(request);

        verify(submissionService, times(1)).submitAssignment(request);
    }

    @Test
    void testGrade_VerifyServiceCall() {
        GradeRequest gradeRequest = new GradeRequest();
        gradeRequest.setScore(7.5);
        gradeRequest.setFeedback("Pass");

        SubmissionResponse mockResponse = SubmissionResponse.builder().build();
        when(submissionService.gradeSubmission(2L, 7.5, "Pass")).thenReturn(mockResponse);

        submissionController.grade(2L, gradeRequest);

        verify(submissionService, times(1)).gradeSubmission(2L, 7.5, "Pass");
    }

    @Test
    void testController_NonNullResponse() {
        SubmitRequest request = new SubmitRequest();
        request.setUserId(1L);
        request.setCourseId(10L);

        SubmissionResponse mockResponse = SubmissionResponse.builder().build();
        when(submissionService.submitAssignment(any(SubmitRequest.class))).thenReturn(mockResponse);

        ResponseEntity<SubmissionResponse> response = submissionController.submit(request);

        assertNotNull(response);
        assertNotNull(response.getBody());
    }
}