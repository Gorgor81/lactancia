package com.lactancia.api.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("/verify-token")
    public String verifyToken(@RequestHeader("Authorization") String token) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            String uid = decodedToken.getUid();
            return "Token válido para el usuario con ID: " + uid;
        } catch (Exception e) {
            return "Token inválido: " + e.getMessage();
        }
    }
}
