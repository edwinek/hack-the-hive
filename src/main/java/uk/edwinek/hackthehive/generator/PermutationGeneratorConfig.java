package uk.edwinek.hackthehive.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class PermutationGeneratorConfig {

    private static final String ALPHABET_KEY = "generator.alphabet";
    private static final String PASSWORD_LENGTH_KEY = "generator.length";

    @Autowired
    private Environment environment;

    @SuppressWarnings("ConstantConditions")
    @Bean
    public PermutationGenerator permutationGenerator() {
        return new PermutationGeneratorImpl(environment.getProperty(ALPHABET_KEY),
                Integer.valueOf(environment.getProperty(PASSWORD_LENGTH_KEY)));
    }
}
