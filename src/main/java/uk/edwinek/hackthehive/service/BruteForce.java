package uk.edwinek.hackthehive.service;

import java.util.Optional;

public interface BruteForce {
    /**
     * Attempts to brute force the login and cease when a successful password is found.
     *
     * @return an optional containing the successful password, if it is found.
     */
    Optional<String> attemptUserLogin();
}
