package com.grepiu.www.process.common.config.db;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 *
 * Firebase 헬퍼 클레스
 *
 * @auth jm
 * @since 2018.05
 */
@Component
public class FirebaseConfig {
    private static DatabaseReference database;

    @Bean
    public DatabaseReference firebaseDatabase() {
        DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
        return firebase;
    }

    @Value("${com.grepiu.firebase.config.url}")
    private String databaseUrl;

    @Value("${com.grepiu.firebase.config.path}")
    private String configPath;

    @PostConstruct
    public void init() throws Exception {
        InputStream accountService = new FileInputStream(configPath);

        FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(accountService))
            .setDatabaseUrl(databaseUrl)
            .build();
        FirebaseApp.initializeApp(options);
    }
}
