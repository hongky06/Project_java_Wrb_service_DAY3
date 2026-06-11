package com.project.project_java_webservice_quanlykhoahocvachamdiem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor  // Sinh constructor không tham số
@AllArgsConstructor // Sinh constructor chứa ĐẦY ĐỦ tham số (Sẽ giải quyết được lỗi của ông)
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private String username;
    private String role;
}