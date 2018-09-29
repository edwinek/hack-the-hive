package uk.edwinek.hackthehive.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HiveClientConfig {

    private static final String URL_KEY = "client.url";

    @Autowired
    private Environment environment;

    @Bean
    public HiveClient hiveClient() {
        return new HiveClientImpl(new RestTemplate(clientHttpRequestFactory()), environment.getProperty(URL_KEY));
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(2000);
        factory.setConnectTimeout(2000);
        return factory;
    }
}
