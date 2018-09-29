package uk.edwinek.hackthehive.generator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.pow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PermutationGeneratorTestConfig.class})
class PermutationGeneratorImplTest {

    @Autowired
    @Qualifier("big")
    private PermutationGenerator permutationGeneratorBig;

    @Autowired
    @Qualifier("threeLongThreeLetters")
    private PermutationGenerator permutationGeneratorThreeLongThreeLetters;

    @Autowired
    @Qualifier("singleNumber")
    private PermutationGenerator permutationGeneratorSingleNumber;

    @Test
    void testLettersNumber() {
        Set<String> expectedResult = new HashSet<>(Arrays.asList("AAA", "AAB", "AAC", "ABA", "ABB", "ABC", "ACA", "ACB", "ACC", "BAA", "BAB", "BAC", "BBA", "BBB", "BBC", "BCA", "BCB", "BCC", "CAA", "CAB", "CAC", "CBA", "CBB", "CBC", "CCA", "CCB", "CCC"));
        assertEquals(expectedResult, permutationGeneratorThreeLongThreeLetters.getPermutations());
    }

    @Test
    void testSingleNumber() {
        Set<String> expectedResult = new HashSet<>(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));
        assertEquals(expectedResult, permutationGeneratorSingleNumber.getPermutations());
    }

    @Test
    void testLots() {
        assertEquals(pow(22,5), permutationGeneratorBig.getPermutations().size());
    }
}
