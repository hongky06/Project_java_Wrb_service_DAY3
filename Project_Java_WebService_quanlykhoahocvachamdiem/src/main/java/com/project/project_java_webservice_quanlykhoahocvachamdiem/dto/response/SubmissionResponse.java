package com.project.project_java_webservice_quanlykhoahocvachamdiem.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionResponse {
    private Long id;
    private Long userId;
    private Long courseId;
    private String githubLink;
    private String reportUrl;
    private Double score;
    private String feedback;
    private String status;
}