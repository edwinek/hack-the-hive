package uk.edwinek.hackthehive.client;

import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
public class HiveClientImpl implements HiveClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(HiveClientImpl.class);
    private final String loginUrl;
    private final RestTemplate restTemplate;

    HiveClientImpl(final RestTemplate restTemplate, final String loginUrl) {
        this.restTemplate = restTemplate;
        this.loginUrl = loginUrl;
    }

    @Override
    public boolean attemptLogin(final String username, final String password) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
        HttpEntity<HiveClientLoginRequest> httpEntity = new HttpEntity<>(
                new HiveClientLoginRequest(username, password), requestHeaders);
        try {
            restTemplate.exchange(loginUrl, HttpMethod.POST, httpEntity, String.class);
            LOGGER.info("success with username: {}, password: {}", username, password);
            return true;
        } catch (HttpClientErrorException e) {
            if (HttpStatus.UNAUTHORIZED.equals(e.getStatusCode())) {
                LOGGER.info("access denied with username: {}, password: {}", username, password);
            } else {
                throw new RuntimeException(
                        "There was an unexpected HTTP Status code of " + e.getStatusCode());
            }
        } catch (ResourceAccessException e) {
            LOGGER.error("failed with username: {}, password: {}", username, password);
        }
        return false;
    }
}
