package com.project.project_java_webservice_quanlykhoahocvachamdiem.service;

import com.project.project_java_webservice_quanlykhoahocvachamdiem.dto.request.SubmitRequest;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.dto.response.SubmissionResponse;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.entity.Course;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.entity.Submission;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.entity.User;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.repository.CourseRepository;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.repository.SubmissionRepository;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SubmissionServiceTest {

    @Mock
    private SubmissionRepository submissionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private SubmissionService submissionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // UT-01: Kiểm tra nộp bài thành công với Link GitHub (FR-07)
    @Test
    void testSubmitAssignment_SuccessWithGithubLink() {
        SubmitRequest request = new SubmitRequest();
        request.setUserId(1L);
        request.setCourseId(10L);
        request.setGithubLink("https://github.com/dohon/project-java");

        User mockUser = new User();
        mockUser.setId(1L);

        Course mockCourse = new Course();
        mockCourse.setId(10L);

        Submission savedSubmission = Submission.builder()
                .user(mockUser)
                .course(mockCourse)
                .githubLink("https://github.com/dohon/project-java")
                .status("SUBMITTED")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(courseRepository.findById(10L)).thenReturn(Optional.of(mockCourse));
        when(submissionRepository.save(any(Submission.class))).thenReturn(savedSubmission);

        SubmissionResponse result = submissionService.submitAssignment(request);

        assertNotNull(result);
        assertEquals("https://github.com/dohon/project-java", result.getGithubLink());
        verify(submissionRepository, times(1)).save(any(Submission.class));
    }

    // UT-02: Kiểm tra nộp bài thành công với File báo cáo (FR-07)
    @Test
    void testSubmitAssignment_SuccessWithReportUrl() {
        SubmitRequest request = new SubmitRequest();
        request.setUserId(1L);
        request.setCourseId(10L);
        request.setReportUrl("https://storage.com/report.pdf");

        User mockUser = new User();
        mockUser.setId(1L);

        Course mockCourse = new Course();
        mockCourse.setId(10L);

        Submission savedSubmission = Submission.builder()
                .user(mockUser)
                .course(mockCourse)
                .reportUrl("https://storage.com/report.pdf")
                .status("SUBMITTED")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(courseRepository.findById(10L)).thenReturn(Optional.of(mockCourse));
        when(submissionRepository.save(any(Submission.class))).thenReturn(savedSubmission);

        SubmissionResponse result = submissionService.submitAssignment(request);

        assertNotNull(result);
        assertEquals("https://storage.com/report.pdf", result.getReportUrl());
    }

    // UT-03: Kiểm tra giảng viên chấm điểm & để lại feedback thành công (FR-08)
    @Test
    void testGradeSubmission_Success() {
        User mockUser = new User();
        mockUser.setId(1L);

        Course mockCourse = new Course();
        mockCourse.setId(1L);

        Submission mockSubmission = Submission.builder()
                .user(mockUser)
                .course(mockCourse)
                .status("SUBMITTED")
                .score(0.0)
                .build();
        mockSubmission.setId(1L);

        when(submissionRepository.findById(1L)).thenReturn(Optional.of(mockSubmission));
        when(submissionRepository.save(any(Submission.class))).thenReturn(mockSubmission);

        SubmissionResponse result = submissionService.gradeSubmission(1L, 9.5, "Cấu trúc tốt!");

        assertNotNull(result);
        assertEquals(9.5, result.getScore());
        assertEquals("Cấu trúc tốt!", result.getFeedback());
        verify(submissionRepository, times(1)).findById(1L);
    }

    // UT-04: Kiểm tra chấm điểm thất bại khi không tồn tại ID bài nộp (FR-08 boundary)
    @Test
    void testGradeSubmission_ThrowsExceptionWhenNotFound() {
        when(submissionRepository.findById(999L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            submissionService.gradeSubmission(999L, 8.0, "Good");
        });

        assertEquals("Submission not found: 999", exception.getMessage());
        verify(submissionRepository, never()).save(any(Submission.class));
    }

    // UT-05: Kiểm tra ràng buộc dữ liệu chính xác giữa Học viên và Khóa học trên bài nộp
    @Test
    void testSubmissionBinding_StudentAndCourse() {
        SubmitRequest request = new SubmitRequest();
        request.setUserId(5L);
        request.setCourseId(10L);

        User mockUser = new User();
        mockUser.setId(5L);
        mockUser.setUsername("ky_hoc_vien");

        Course mockCourse = new Course();
        mockCourse.setId(10L);

        Submission savedSubmission = Submission.builder()
                .user(mockUser)
                .course(mockCourse)
                .status("SUBMITTED")
                .build();

        when(userRepository.findById(5L)).thenReturn(Optional.of(mockUser));
        when(courseRepository.findById(10L)).thenReturn(Optional.of(mockCourse));
        when(submissionRepository.save(any(Submission.class))).thenReturn(savedSubmission);

        SubmissionResponse result = submissionService.submitAssignment(request);

        assertNotNull(result);
        assertEquals(5L, result.getUserId());
        assertEquals(10L, result.getCourseId());
    }
}