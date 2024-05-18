package com.youngpopeugene.mainservice.controller;

import com.youngpopeugene.mainservice.service.AuthService;
import com.youngpopeugene.mainservice.model.auth.LoginRequest;
import com.youngpopeugene.mainservice.model.auth.AuthResponse;
import com.youngpopeugene.mainservice.model.auth.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping(value = "/register", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return service.register(request);
    }

    @PostMapping(value = "/login", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public AuthResponse authenticate(@RequestBody LoginRequest request) {
        return service.authenticate(request);
    }

    @PostMapping(value = "/refresh-token", consumes = APPLICATION_JSON_VALUE)
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.refreshToken(request, response);
    }
}