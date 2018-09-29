package uk.edwinek.hackthehive.client;

import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.edwinek.hackthehive.client.HiveClientTestConfig.REST_TEMPLATE_MOCK;
import static uk.edwinek.hackthehive.client.HiveClientTestConfig.TEST_URL;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HiveClientTestConfig.class})
class HiveClientImplTest {

    @Autowired
    private HiveClient hiveClient;

    @Test
    @DisplayName("Should return true when no exceptions thrown.")
    void testSuccessfulLogin() {
        // setup
        final String userName = "user1";
        final String password = "password123";
        // test
        assertTrue(hiveClient.attemptLogin(userName, password));
        // check
        verify(REST_TEMPLATE_MOCK, times(1)).exchange(TEST_URL, HttpMethod.POST, getEntityFromCredentials(userName,
                password), String.class);
    }

    @Test
    @DisplayName("Should return false when access denied.")
    void testAccessDenied() {
        // setup
        final String userName = "user2";
        final String password = "password123";
        when(REST_TEMPLATE_MOCK.exchange(TEST_URL, HttpMethod.POST, getEntityFromCredentials(userName, password),
                String.class)).thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED));
        // test
        assertFalse(hiveClient.attemptLogin(userName, password));
        // check
        verify(REST_TEMPLATE_MOCK, times(1)).exchange(TEST_URL, HttpMethod.POST, getEntityFromCredentials(userName,
                password), String.class);
    }

    @Test
    @DisplayName("Should return false when timeout.")
    void testTimeout() {
        // setup
        final String userName = "username";
        final String password = "p455w0rd";
        when(REST_TEMPLATE_MOCK.exchange(TEST_URL, HttpMethod.POST, getEntityFromCredentials(userName, password),
                String.class)).thenThrow(new ResourceAccessException(""));
        // test
        assertFalse(hiveClient.attemptLogin(userName, password));
        // check
        verify(REST_TEMPLATE_MOCK, times(1)).exchange(TEST_URL, HttpMethod.POST, getEntityFromCredentials(userName,
                password), String.class);
    }

    @Test
    @DisplayName("Should throw exception on unexpected response.")
    void testUnexpectedResponse() {
        // setup
        final String userName = "user5";
        final String password = "123";
        when(REST_TEMPLATE_MOCK.exchange(TEST_URL, HttpMethod.POST, getEntityFromCredentials(userName, password),
                String.class)).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));
        // test
        assertThrows(RuntimeException.class, () -> assertFalse(hiveClient.attemptLogin(userName, password)));
        // check
        verify(REST_TEMPLATE_MOCK, times(1)).exchange(TEST_URL, HttpMethod.POST, getEntityFromCredentials(userName,
                password), String.class);
    }

    private HttpEntity<HiveClientLoginRequest> getEntityFromCredentials(final String username, final String password) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
        return new HttpEntity<>(
                new HiveClientLoginRequest(username, password), requestHeaders);
    }
}
