package uk.edwinek.hackthehive.generator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class PermutationGeneratorImpl implements PermutationGenerator {

    private char[] alphabetChars;
    private int length;
    private int[] currentPermutation;

    PermutationGeneratorImpl(
            @Value("${generator.alphabet}") final String alphabet,
            @Value("${generator.length}") final int length
    ) {
        this.alphabetChars = alphabet.toCharArray();
        this.length = length;
    }

    public Set<String> getPermutations() {
        this.currentPermutation = new int[length];
        Set<String> permutations = new LinkedHashSet<>(length);
        while (!onLastPermutation()) {
            permutations.add(getCurrent());
            nextPermutation();
        }
        permutations.add(getCurrent());
        return permutations;
    }

    private String getCurrent() {
        @SuppressWarnings("StringBufferMayBeStringBuilder")
        StringBuffer currentBuffer = new StringBuffer(length);
        for (int charProgress : currentPermutation) {
            currentBuffer.append(alphabetChars[charProgress]);
        }
        return currentBuffer.toString();
    }

    private void nextPermutation() {
        int totalPossible = alphabetChars.length - 1;

        for (int i = length - 1; i >= 0; i--) {
            if (currentPermutation[i] < totalPossible) {
                currentPermutation[i] = currentPermutation[i] + 1;
                break;
            } else {
                if (i > 0) {
                    currentPermutation[i] = 0;
                }
            }
        }
    }

    private boolean onLastPermutation() {
        @SuppressWarnings("StringBufferMayBeStringBuilder")
        StringBuffer lastBuffer = new StringBuffer(length);
        for (int ignored : currentPermutation) {
            lastBuffer.append(alphabetChars[alphabetChars.length - 1]);
        }
        return getCurrent().equals(lastBuffer.toString());
    }
}
