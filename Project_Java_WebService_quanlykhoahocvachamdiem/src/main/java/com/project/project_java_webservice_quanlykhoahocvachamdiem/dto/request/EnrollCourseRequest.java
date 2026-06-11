package com.project.project_java_webservice_quanlykhoahocvachamdiem.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollCourseRequest {
    @NotNull(message = "userId không được để trống")
    private Long userId;

    @NotNull(message = "courseId không được để trống")
    private Long courseId;
}