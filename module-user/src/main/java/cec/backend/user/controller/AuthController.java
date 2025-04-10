package cec.backend.user.controller;

import cec.backend.user.dto.auth.*;
import cec.backend.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-in")
    public ResponseEntity<AuthSignInResponse> signIn(@RequestBody AuthSignInRequest request) {
        return ResponseEntity.ok(authService.signIn(request));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<AuthPasswordResetResponse> resetPassword(@RequestBody AuthPasswordResetRequest request) {
        return ResponseEntity.ok(authService.resetPassword(request));
    }

    @PostMapping("/verify-reset-token")
    public ResponseEntity<AuthVerifyResetTokenResponse> verifyResetToken(@RequestBody AuthVerifyResetTokenRequest request) {
        return ResponseEntity.ok(authService.verifyResetToken(request));
    }

    @PostMapping("/reset-password/confirm")
    public ResponseEntity<AuthResetPasswordResponse> confirmResetPassword(@RequestBody AuthResetPasswordRequest request) {
        return ResponseEntity.ok(authService.confirmResetPassword(request));
    }

    @GetMapping("/me")
    public ResponseEntity<MyInformationResponse> getMyInformation() {
        return ResponseEntity.ok(authService.getMyInformation());
    }

    @PutMapping("/me")
    public ResponseEntity<ChangeMyInformationResponse> updateMyInformation(@RequestBody ChangeMyInformationRequest request) {
        return ResponseEntity.ok(authService.updateMyInformation(request));
    }

    @PostMapping("/email/request-change")
    public ResponseEntity<EmailUpdateResponse> requestEmailChange() {
        return ResponseEntity.ok(authService.requestEmailChange());
    }

    @PostMapping("/email/verify-change")
    public ResponseEntity<EmailChangeVerifyResponse> verifyEmailChange(@RequestBody EmailChangeVerifyRequest request) {
        return ResponseEntity.ok(authService.verifyEmailChange(request));
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<TokenRefreshResponse> refreshToken(@RequestBody TokenRefreshRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }
} 