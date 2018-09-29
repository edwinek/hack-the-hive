package uk.edwinek.hackthehive.progress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.nio.file.Paths;

@Configuration
public class PreviousProgressConfig {

    private final static String PROGRESS_FILENAME_KEY = "progress.filename";
    private final static String PROGRESS_USERNAME_KEY = "progress.username";

    @Autowired
    private Environment environment;

    @Bean
    public PreviousProgress previousProgress() {
        return new PreviousProgressImpl(Paths.get(environment.getProperty(PROGRESS_FILENAME_KEY)),
                environment.getProperty(PROGRESS_USERNAME_KEY));
    }
}