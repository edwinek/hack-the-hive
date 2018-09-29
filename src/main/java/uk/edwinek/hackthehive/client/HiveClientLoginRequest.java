package uk.edwinek.hackthehive.client;

import java.util.Objects;

@SuppressWarnings("unused")
public class HiveClientLoginRequest {

    private String username;
    private String passcode;

    public HiveClientLoginRequest(final String username, final String passcode) {
        this.username = username;
        this.passcode = passcode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(final String passcode) {
        this.passcode = passcode;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final HiveClientLoginRequest that = (HiveClientLoginRequest) o;
        return Objects.equals(username, that.username) && Objects.equals(passcode,
                that.passcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, passcode);
    }
}
