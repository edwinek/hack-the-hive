package uk.edwinek.hackthehive.client;

public interface HiveClient {
    /**
     * Attempts to login to the Hive login endpoint with the specified credentials.
     *
     * @param username the username to attempt to log in in with.
     * @param password the password to attempt to log in with.
     * @return true if the login was successful; false a log was attempt but got access denied.
     */
    boolean attemptLogin(final String username, final String password);
}
