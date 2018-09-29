package uk.edwinek.hackthehive.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.mock;

@Configuration
public class HiveClientTestConfig {

    static final RestTemplate REST_TEMPLATE_MOCK = mock(RestTemplate.class);
    static final String TEST_URL = "http://example.com/rest/login";

    @Bean
    public HiveClient hiveClient() {
        return new HiveClientImpl(REST_TEMPLATE_MOCK, TEST_URL);
    }
}
