package com.example.contact.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class AuthController {

    private static final String ADMIN_USERNAME = "UmeshShiva";
    private static final String ADMIN_PASSWORD = "Subha$10012006";

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
            return Map.of("status", "success", "message", "Login successful");
        } else {
            return Map.of("status", "error", "message", "Invalid credentials");
        }
    }
}
