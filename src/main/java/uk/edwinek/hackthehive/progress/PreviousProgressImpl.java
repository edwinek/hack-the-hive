package uk.edwinek.hackthehive.progress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class PreviousProgressImpl implements PreviousProgress {

    private static final String SUCCESS_REGEX_SKELETON = "^.*INFO.*success.*username: %s, password: (.*)";
    private static final String ACCESS_DENIED_REGEX_SKELETON = "^.*INFO.*access denied.*username: %s, password: (.*)";
    private static final Logger LOGGER = LoggerFactory.getLogger(PreviousProgressImpl.class);
    private final Path outputFile;
    private final String userName;

    PreviousProgressImpl(@Value("${progress.filename}") final Path outputFile,
                         @Value("${progress.username}") final String userName) {
        this.outputFile = outputFile;
        this.userName = userName;
    }

    @Override
    public Optional<String> loadSuccess() {
        final String successRegex = String.format(SUCCESS_REGEX_SKELETON, userName);
        Optional<String> usedPasswords = Optional.empty();
        try (Stream<String> stream = Files.lines(outputFile)) {
            usedPasswords =
                    stream.filter(s -> s.matches(successRegex)).map(s -> s.replaceAll(successRegex, "$1")).findFirst();
        } catch (IOException e) {
            LOGGER.info("Couldn't find a previous run.");
        }
        return usedPasswords;
    }

    @Override
    public Set<String> loadAttemptsFromPreviousLog() {
        final String accessDeniedRegex = String.format(ACCESS_DENIED_REGEX_SKELETON, userName);
        Set<String> usedPasswords = new HashSet<>();
        try (Stream<String> stream = Files.lines(outputFile)) {
            usedPasswords = stream.filter(s -> s.matches(accessDeniedRegex)).map(s -> s.replaceAll(accessDeniedRegex,
                    "$1")).collect(Collectors.toSet());
        } catch (IOException e) {
            LOGGER.info("Couldn't find a previous run.");
        }
        return usedPasswords;
    }
}
