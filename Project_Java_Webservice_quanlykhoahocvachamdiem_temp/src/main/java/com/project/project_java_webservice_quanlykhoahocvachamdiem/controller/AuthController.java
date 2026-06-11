package com.project.project_java_webservice_quanlykhoahocvachamdiem.controller;

import com.project.project_java_webservice_quanlykhoahocvachamdiem.dto.request.LoginRequest;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.dto.request.RegisterRequest;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.dto.response.LoginResponse;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.dto.response.UserResponse;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestParam String refreshToken) {
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestParam String accessToken,
            @RequestParam String refreshToken) {
        authService.logout(accessToken, refreshToken);
        return ResponseEntity.ok("Đăng xuất thành công");
    }
}