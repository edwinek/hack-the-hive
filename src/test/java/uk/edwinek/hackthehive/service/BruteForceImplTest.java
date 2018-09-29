package uk.edwinek.hackthehive.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static uk.edwinek.hackthehive.service.BruteForceTestConfig.HIVE_CLIENT_MOCK;
import static uk.edwinek.hackthehive.service.BruteForceTestConfig.PERMUTATION_GENERATOR_MOCK;
import static uk.edwinek.hackthehive.service.BruteForceTestConfig.PREVIOUS_PROGRESS_MOCK;
import static uk.edwinek.hackthehive.service.BruteForceTestConfig.TEST_USERNAME;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BruteForceTestConfig.class})
class BruteForceImplTest {

    @Autowired
    private BruteForce bruteForce;

    @BeforeEach
    void setup() {
        reset(HIVE_CLIENT_MOCK, PERMUTATION_GENERATOR_MOCK, PREVIOUS_PROGRESS_MOCK);
    }

    @Test
    @DisplayName("Should attempt all permutations if password not found and no previous progress.")
    void testNoPreviousProgressNoSuccess() {
        // setup
        final HashSet<String> permutations = new HashSet<>(Arrays.asList("ABC", "ABB", "CCC"));
        when(PERMUTATION_GENERATOR_MOCK.getPermutations()).thenReturn(permutations);
        when(PREVIOUS_PROGRESS_MOCK.loadAttemptsFromPreviousLog()).thenReturn(new HashSet<>());
        // test
        assertEquals(Optional.empty(), bruteForce.attemptUserLogin());
        // check
        permutations.forEach(password -> verify(HIVE_CLIENT_MOCK, times(1)).attemptLogin(TEST_USERNAME, password));
    }

    @Test
    @DisplayName("Should not attempt permutations from previous progress.")
    void testPreviousProgressNoSuccess() {
        // setup
        final HashSet<String> permutations = new HashSet<>(Arrays.asList("ABC", "ABB", "CCC", "DDD"));
        final HashSet<String> previousProgress = new HashSet<>(Arrays.asList("CCC", "DDD"));
        final HashSet<String> permutationToTry = new HashSet<>(Arrays.asList("ABC", "ABB"));
        when(PERMUTATION_GENERATOR_MOCK.getPermutations()).thenReturn(permutations);
        when(PREVIOUS_PROGRESS_MOCK.loadAttemptsFromPreviousLog()).thenReturn(previousProgress);
        // test
        assertEquals(Optional.empty(), bruteForce.attemptUserLogin());
        // check
        permutationToTry.forEach(password -> verify(HIVE_CLIENT_MOCK, times(1)).attemptLogin(TEST_USERNAME, password));
    }

    @Test
    @DisplayName("Should only run with successful password if exists in previous progress.")
    void testSuccessFromPreviousProgress() {
        // setup
        final String successfulPassword = "password123";
        when(PREVIOUS_PROGRESS_MOCK.loadSuccess()).thenReturn(Optional.of(successfulPassword));
        // test
        assertEquals(Optional.empty(), bruteForce.attemptUserLogin());
        // check
        verify(HIVE_CLIENT_MOCK, times(1)).attemptLogin(TEST_USERNAME, successfulPassword);
        verify(PERMUTATION_GENERATOR_MOCK, times(0)).getPermutations();
        verify(PREVIOUS_PROGRESS_MOCK, times(0)).loadAttemptsFromPreviousLog();
        verifyNoMoreInteractions(HIVE_CLIENT_MOCK);
    }

    @Test
    @DisplayName("Should return password on successful login.")
    void testSuccessfulLogon(){
            // setup
            final String successfulPassword = "logmein";
            final HashSet<String> permutations = new HashSet<>(Arrays.asList("ABC", successfulPassword, "CCC", "DDD"));
            final HashSet<String> previousProgress = new HashSet<>(Arrays.asList("CCC", "DDD"));
            when(PERMUTATION_GENERATOR_MOCK.getPermutations()).thenReturn(permutations);
            when(PREVIOUS_PROGRESS_MOCK.loadAttemptsFromPreviousLog()).thenReturn(previousProgress);
            when(HIVE_CLIENT_MOCK.attemptLogin(TEST_USERNAME, successfulPassword)).thenReturn(true);
            // test
            assertEquals(Optional.of(successfulPassword), bruteForce.attemptUserLogin());
            // check
            verify(HIVE_CLIENT_MOCK, times(1)).attemptLogin(TEST_USERNAME, successfulPassword);
        }
}
