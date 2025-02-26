package com.lactancia.api.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp initializeFirebase() throws IOException {
        // Obtener el valor de la variable de entorno FIREBASE_CONFIG
        String firebaseConfig = System.getenv("FIREBASE_CONFIG");

        if (firebaseConfig == null || firebaseConfig.isEmpty()) {
            throw new IOException("La variable de entorno FIREBASE_CONFIG no está configurada.");
        }

        FirebaseOptions options;
        
        if (firebaseConfig.startsWith("{")) {
            // Si el valor de FIREBASE_CONFIG parece ser un JSON, lo tratamos como contenido directo
            ByteArrayInputStream serviceAccount = new ByteArrayInputStream(firebaseConfig.getBytes());
            
            options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            
        } else {
            // Si no, tratamos el valor como una ruta a un archivo
            FileInputStream serviceAccount = new FileInputStream(firebaseConfig);
            options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
        }

        return FirebaseApp.initializeApp(options);
    }
}
