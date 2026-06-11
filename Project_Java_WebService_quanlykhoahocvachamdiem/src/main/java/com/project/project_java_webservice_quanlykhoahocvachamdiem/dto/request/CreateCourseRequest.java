package com.project.project_java_webservice_quanlykhoahocvachamdiem.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCourseRequest {
    @NotBlank(message = "Tên khóa học không được để trống")
    private String name;

    private String description;
    private Long lecturerId;
}