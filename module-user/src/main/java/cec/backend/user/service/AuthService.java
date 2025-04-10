package cec.backend.user.service;

import cec.backend.core.model.entity.User;
import cec.backend.core.service.RefreshTokenService;
import cec.backend.user.dto.auth.*;
import cec.backend.user.repository.UserRepository;
import cec.backend.user.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final RedisTemplate<String, String> redisTemplate;

    private static final String PASSWORD_RESET_PREFIX = "password:reset:";
    private static final String EMAIL_CHANGE_PREFIX = "email:change:";
    private static final long TOKEN_EXPIRE_TIME = 30; // 30분

    @Transactional
    public AuthSignInResponse signIn(AuthSignInRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        String accessToken = jwtTokenProvider.createAccessToken(user.getId(), user.getRole());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getId());

        refreshTokenService.saveRefreshToken(user.getId(), refreshToken);

        return AuthSignInResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .role(user.getRole())
                .build();
    }

    @Transactional
    public AuthPasswordResetResponse resetPassword(AuthPasswordResetRequest request) {
        userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String resetToken = UUID.randomUUID().toString();
        String key = PASSWORD_RESET_PREFIX + request.getEmail();
        redisTemplate.opsForValue().set(key, resetToken, TOKEN_EXPIRE_TIME, TimeUnit.MINUTES);

        return AuthPasswordResetResponse.builder()
                .message("Password reset token has been sent")
                .resetToken(resetToken)
                .build();
    }

    public AuthVerifyResetTokenResponse verifyResetToken(AuthVerifyResetTokenRequest request) {
        String key = PASSWORD_RESET_PREFIX + request.getEmail();
        String storedToken = redisTemplate.opsForValue().get(key);

        boolean isValid = storedToken != null && storedToken.equals(request.getResetToken());

        return AuthVerifyResetTokenResponse.builder()
                .isValid(isValid)
                .message(isValid ? "Valid reset token" : "Invalid reset token")
                .build();
    }

    @Transactional
    public AuthResetPasswordResponse confirmResetPassword(AuthResetPasswordRequest request) {
        String key = PASSWORD_RESET_PREFIX + request.getEmail();
        String storedToken = redisTemplate.opsForValue().get(key);

        if (storedToken == null || !storedToken.equals(request.getResetToken())) {
            throw new IllegalArgumentException("Invalid reset token");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.changePassword(passwordEncoder.encode(request.getNewPassword()));
        redisTemplate.delete(key);

        return AuthResetPasswordResponse.builder()
                .success(true)
                .message("Password has been reset successfully")
                .build();
    }

    public MyInformationResponse getMyInformation() {
        String userId = getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return MyInformationResponse.from(user);
    }

    @Transactional
    public ChangeMyInformationResponse updateMyInformation(ChangeMyInformationRequest request) {
        String userId = getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.changeNickname(request.getNickname());
        user.changePhoneNumber(request.getPhoneNumber());
        user.changeProfilePicture(request.getProfilePicture());

        return ChangeMyInformationResponse.from(user);
    }

    @Transactional
    public EmailUpdateResponse requestEmailChange() {
        String userId = getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String verificationToken = UUID.randomUUID().toString();
        String key = EMAIL_CHANGE_PREFIX + user.getId();
        redisTemplate.opsForValue().set(key, verificationToken, TOKEN_EXPIRE_TIME, TimeUnit.MINUTES);

        return EmailUpdateResponse.builder()
                .message("Email verification token has been sent")
                .verificationToken(verificationToken)
                .build();
    }

    @Transactional
    public EmailChangeVerifyResponse verifyEmailChange(EmailChangeVerifyRequest request) {
        String userId = getCurrentUserId();
        String key = EMAIL_CHANGE_PREFIX + userId;
        String storedToken = redisTemplate.opsForValue().get(key);

        if (storedToken == null || !storedToken.equals(request.getVerificationToken())) {
            throw new IllegalArgumentException("Invalid verification token");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (userRepository.existsByEmail(request.getNewEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        user.changeEmail(request.getNewEmail());
        redisTemplate.delete(key);

        return EmailChangeVerifyResponse.builder()
                .success(true)
                .message("Email has been changed successfully")
                .newEmail(request.getNewEmail())
                .build();
    }

    @Transactional
    public TokenRefreshResponse refreshToken(TokenRefreshRequest request) {
        if (!jwtTokenProvider.validateToken(request.getRefreshToken())) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        String userId = jwtTokenProvider.getUserId(request.getRefreshToken());
        String storedToken = refreshTokenService.getRefreshToken(userId);

        if (storedToken == null || !storedToken.equals(request.getRefreshToken())) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String newAccessToken = jwtTokenProvider.createAccessToken(userId, user.getRole());
        String newRefreshToken = jwtTokenProvider.createRefreshToken(userId);

        refreshTokenService.saveRefreshToken(userId, newRefreshToken);

        return TokenRefreshResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    private String getCurrentUserId() {
        // SecurityContext에서 현재 사용자의 ID를 가져오는 로직
        // 실제 구현은 SecurityConfig와 함께 구현해야 함
        throw new UnsupportedOperationException("Not implemented yet");
    }
}