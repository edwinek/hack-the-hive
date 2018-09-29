package uk.edwinek.hackthehive.progress;

import java.util.Optional;
import java.util.Set;

public interface PreviousProgress {
    /**
     * Retrieves any successful passwords from a previous output.log file.
     *
     * @return an optional of the password of a successful login.
     */
    Optional<String> loadSuccess();

    /**
     * Retrieves any used passwords from a previous output.log file.
     *
     * @return a list of passwords that a previous run was denied access with.
     */
    Set<String> loadAttemptsFromPreviousLog();
}
