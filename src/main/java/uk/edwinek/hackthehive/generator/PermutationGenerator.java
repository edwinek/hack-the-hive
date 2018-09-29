package uk.edwinek.hackthehive.generator;

import java.util.Set;

public interface PermutationGenerator {
    /**
     * Returns a the set of all permutations.
     *
     * @return the set of all permutations;
     */
    Set<String> getPermutations();
}
