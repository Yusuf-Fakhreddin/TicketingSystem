package com.YusufFakhreddin.ICDTicketingSystem.auth;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authentcationService;

    public AuthController(AuthenticationService authentcationService) {
        this.authentcationService = authentcationService;
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authentcationService.login(authenticationRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody RegisterRequest authenticationRequest) {
        return ResponseEntity.ok(authentcationService.register(authenticationRequest));
    }
}