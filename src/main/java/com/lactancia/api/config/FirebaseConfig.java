package com.lactancia.api.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Bean
    public void initializeFirebase() throws IOException {
        String firebaseConfig = System.getenv("FIREBASE_CONFIG"); // Leer desde la variable de entorno

        if (firebaseConfig == null || firebaseConfig.isEmpty()) {
            throw new IOException("FIREBASE_CONFIG no está configurado");
        }

        FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(new ByteArrayInputStream(firebaseConfig.getBytes())))
            .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
    }
}
