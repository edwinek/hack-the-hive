package uk.edwinek.hackthehive.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import uk.edwinek.hackthehive.client.HiveClient;
import uk.edwinek.hackthehive.generator.PermutationGenerator;
import uk.edwinek.hackthehive.progress.PreviousProgress;

@Configuration
public class BruteForceConfig {

    private static final String USERNAME_KEY = "service.username";

    @Autowired
    private Environment environment;

    @Autowired
    private HiveClient hiveClient;

    @Autowired
    private PermutationGenerator permutationGenerator;

    @Autowired
    private PreviousProgress previousProgress;

    @Bean
    public BruteForce bruteForce() {
        return new BruteForceImpl(hiveClient, permutationGenerator, previousProgress, environment.getProperty(
                USERNAME_KEY));
    }
}
