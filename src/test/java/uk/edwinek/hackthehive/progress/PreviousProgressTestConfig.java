package uk.edwinek.hackthehive.progress;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class PreviousProgressTestConfig {

    @Bean("withoutPreviousAttempts")
    public PreviousProgress previousProgressWithoutPreviousAttempts() {
        return new PreviousProgressImpl(Paths.get("test"), "admin");
    }

    @Bean("withSuccess")
    public PreviousProgress previousProgressWithSuccess() {
        return new PreviousProgressImpl(getPathFromResourceFile("success-test.log"), "admin");
    }

    @Bean("withoutSuccess")
    public PreviousProgress previousProgressWithoutSuccess() {
        return new PreviousProgressImpl(getPathFromResourceFile("progress-test.log"), "admin");
    }

    private Path getPathFromResourceFile(String resourceFileName) {
        try {
            return ResourceUtils.getFile("classpath:" + resourceFileName).toPath();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Could not get resource file with name: " + resourceFileName);
        }
    }
}
