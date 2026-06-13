package com.project.project_java_webservice_quanlykhoahocvachamdiem.service;

import com.project.project_java_webservice_quanlykhoahocvachamdiem.dto.request.LoginRequest;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.dto.request.RegisterRequest;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.dto.response.LoginResponse;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.dto.response.UserResponse;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.entity.RefreshToken;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.entity.Role;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.entity.User;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.repository.RefreshTokenRepository;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.repository.UserRepository;
import com.project.project_java_webservice_quanlykhoahocvachamdiem.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername()))
            throw new RuntimeException("Username đã tồn tại");
        if (userRepository.existsByEmail(request.getEmail()))
            throw new RuntimeException("Email đã tồn tại");
z
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .fullName(request.getFullName())
                .role(Role.STUDENT)
                .active(true)
                .build();
        userRepository.save(user);

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole().name())
                .build();
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Sai tài khoản hoặc mật khẩu"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new RuntimeException("Sai tài khoản hoặc mật khẩu");

        if (Boolean.FALSE.equals(user.getActive()))
            throw new RuntimeException("Tài khoản đã bị khóa");

        String accessToken = jwtTokenProvider.generateAccessToken(user);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);

        refreshTokenRepository.save(RefreshToken.builder()
                .token(refreshToken).user(user).build());

        return new LoginResponse(accessToken, refreshToken, user.getUsername(), user.getRole().name());
    }

    public LoginResponse refreshToken(String requestRefreshToken) {
        RefreshToken stored = refreshTokenRepository.findByToken(requestRefreshToken)
                .orElseThrow(() -> new RuntimeException("Refresh token không hợp lệ"));

        User user = stored.getUser();
        String newAccessToken = jwtTokenProvider.generateAccessToken(user);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(user);

        refreshTokenRepository.delete(stored);
        refreshTokenRepository.save(RefreshToken.builder()
                .token(newRefreshToken).user(user).build());

        return new LoginResponse(newAccessToken, newRefreshToken, user.getUsername(), user.getRole().name());
    }

    public void logout(String accessToken, String refreshTokenStr) {
        refreshTokenRepository.findByToken(refreshTokenStr)
                .ifPresent(refreshTokenRepository::delete);

        redisTemplate.opsForValue().set(
                "blacklist:" + accessToken, "true", Duration.ofHours(1));
    }

    public boolean isTokenBlacklisted(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey("blacklist:" + token));
    }
}