package uk.edwinek.hackthehive.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import uk.edwinek.hackthehive.client.HiveClientConfig;
import uk.edwinek.hackthehive.generator.PermutationGeneratorConfig;
import uk.edwinek.hackthehive.progress.PreviousProgressConfig;
import uk.edwinek.hackthehive.service.BruteForceConfig;

@Configuration
@Import({
        BruteForceConfig.class,
        HiveClientConfig.class,
        PermutationGeneratorConfig.class,
        PreviousProgressConfig.class
})
@PropertySource("config.properties")
public class HackerConfig {
}
