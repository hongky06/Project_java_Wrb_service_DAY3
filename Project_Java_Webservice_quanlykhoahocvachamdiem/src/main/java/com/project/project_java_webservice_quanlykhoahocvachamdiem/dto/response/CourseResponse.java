package com.project.project_java_webservice_quanlykhoahocvachamdiem.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {
    private Long id;
    private String name;
    private String description;
    private String lecturerName;
}