package com.project.project_java_webservice_quanlykhoahocvachamdiem.service;

import com.project.project_java_webservice_quanlykhoahocvachamdiem.dto.request.SubmitRequest;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.dto.response.SubmissionResponse;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.entity.Course;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.entity.Submission;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.entity.User;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.repository.CourseRepository;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.repository.SubmissionRepository;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public SubmissionResponse submitAssignment(SubmitRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found: " + request.getUserId()));
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found: " + request.getCourseId()));

        Submission submission = Submission.builder()
                .user(user).course(course)
                .githubLink(request.getGithubLink())
                .reportUrl(request.getReportUrl())
                .status("SUBMITTED")
                .build();
        submissionRepository.save(submission);
        return toResponse(submission);
    }

    public SubmissionResponse gradeSubmission(Long id, Double score, String feedback) {
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Submission not found: " + id));

        if (!"SUBMITTED".equals(submission.getStatus()))
            throw new IllegalArgumentException("Bài nộp chưa ở trạng thái SUBMITTED");

        submission.setScore(score);
        submission.setFeedback(feedback);
        submission.setStatus("GRADED");
        submissionRepository.save(submission);
        return toResponse(submission);
    }

    public SubmissionResponse getById(Long id) {
        return toResponse(submissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Submission not found: " + id)));
    }

    private SubmissionResponse toResponse(Submission s) {
        return SubmissionResponse.builder()
                .id(s.getId())
                .userId(s.getUser().getId())
                .courseId(s.getCourse().getId())
                .githubLink(s.getGithubLink())
                .reportUrl(s.getReportUrl())
                .score(s.getScore())
                .feedback(s.getFeedback())
                .status(s.getStatus())
                .build();
    }
}