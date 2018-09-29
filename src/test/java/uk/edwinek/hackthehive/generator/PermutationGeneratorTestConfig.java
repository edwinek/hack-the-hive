package uk.edwinek.hackthehive.generator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PermutationGeneratorTestConfig {

    @Bean("threeLongThreeLetters")
    public PermutationGenerator permutationGeneratorThreeLongThreeLetters() {
        return new PermutationGeneratorImpl("ABC", 3);
    }

    @Bean("singleNumber")
    public PermutationGenerator permutationGeneratorWithSingleNumber() {
        return new PermutationGeneratorImpl("0123456789", 1);
    }

    @Bean("big")
    public PermutationGenerator permutationGeneratorBig() {
        return new PermutationGeneratorImpl("0123456789ABCDEFabcdef", 5);
    }

}
