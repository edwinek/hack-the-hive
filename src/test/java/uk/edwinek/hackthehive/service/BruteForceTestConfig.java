package uk.edwinek.hackthehive.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.edwinek.hackthehive.client.HiveClient;
import uk.edwinek.hackthehive.generator.PermutationGenerator;
import uk.edwinek.hackthehive.progress.PreviousProgress;

import static org.mockito.Mockito.mock;

@Configuration
public class BruteForceTestConfig {

    static final String TEST_USERNAME = "usertohack";
    static final HiveClient HIVE_CLIENT_MOCK = mock(HiveClient.class);
    static final PermutationGenerator PERMUTATION_GENERATOR_MOCK = mock(PermutationGenerator.class);
    static final PreviousProgress PREVIOUS_PROGRESS_MOCK = mock(PreviousProgress.class);

    @Bean
    public BruteForce bruteForce() {
        return new BruteForceImpl(HIVE_CLIENT_MOCK, PERMUTATION_GENERATOR_MOCK, PREVIOUS_PROGRESS_MOCK, TEST_USERNAME);
    }
}
