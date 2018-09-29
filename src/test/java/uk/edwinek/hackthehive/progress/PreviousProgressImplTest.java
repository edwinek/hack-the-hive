package uk.edwinek.hackthehive.progress;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PreviousProgressTestConfig.class})
class PreviousProgressImplTest {

    @Autowired
    @Qualifier("withoutPreviousAttempts")
    private PreviousProgress noPreviousProgress;

    @Autowired
    @Qualifier("withSuccess")
    private PreviousProgress previousProgressWithSuccess;

    @Autowired
    @Qualifier("withoutSuccess")
    private PreviousProgress previousProgressWithoutSuccess;

    @Test
    @DisplayName("Should load a success password from file.")
    void testSuccessHappyPath() {
        Optional<String> previousProgressOptional = previousProgressWithSuccess.loadSuccess();
        assertTrue(previousProgressOptional.isPresent());
        assertEquals("37563", previousProgressOptional.get());
    }

    @Test
    @DisplayName("Should not load a success password from file.")
    void testSuccessWhenNoneOccurred() {
        assertEquals(Optional.empty(), previousProgressWithoutSuccess.loadSuccess());
    }

    @Test
    @DisplayName("Should load expected previous attempts from file.")
    void testLoadAttempts() {
        Set<String> previousAttempts = new HashSet<>(Arrays.asList("001247", "000128", "006559", "000127", "000126",
                "001246", "006561", "003392", "003393", "006560"));
        assertEquals(previousAttempts, previousProgressWithoutSuccess.loadAttemptsFromPreviousLog());
    }

    @Test
    @DisplayName("Should load no previous attempts from file.")
    void testLoadAttemptsWhenNoneExist() {
        assertEquals(new HashSet<>(), noPreviousProgress.loadAttemptsFromPreviousLog());
    }
}
