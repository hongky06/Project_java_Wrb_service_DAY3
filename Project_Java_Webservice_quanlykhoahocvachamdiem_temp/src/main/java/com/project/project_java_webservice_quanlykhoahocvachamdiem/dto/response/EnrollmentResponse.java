package com.project.project_java_webservice_quanlykhoahocvachamdiem.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentResponse {
    private Long id;
    private Long userId;
    private Long courseId;
    private String courseName;
    private String status;
}