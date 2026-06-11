package com.project.project_java_webservice_quanlykhoahocvachamdiem.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradeRequest {
    @NotNull(message = "Score không được để trống")
    @Min(value = 0, message = "Score tối thiểu là 0")
    @Max(value = 100, message = "Score tối đa là 100")
    private Double score;

    private String feedback;
}