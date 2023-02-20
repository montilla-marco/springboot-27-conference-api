package springboot.intro.conference.integration.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import springboot.intro.conference.entity.Speaker;
import springboot.intro.conference.repository.SpeakerRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class SpeakerRepositoryTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private SpeakerRepository repository;

    private final static Long VALID_TEST_ID = 2L;
    private final static Long INVALID_TEST_ID = 0L;
    private final static Long NULL_TEST_ID = null;

    @Test
    void givenNullId_whenFindById_thenThrownIllegalArgumentException() {
        // arange
        String expectedMessage = "The given id must not be null!";

        Exception exception = assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            // act
            Optional<Speaker> found = repository.findById(NULL_TEST_ID);
        });

        // assert
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void givenInvalidId_whenFindById_thenReturnSpeaker() {
        // act
        Optional<Speaker> found = repository.findById(INVALID_TEST_ID);

        // assert
        assertFalse(found.isPresent());
    }

    @Test
    void givenId_whenFindById_thenReturnSpeaker() {
        // act
        Optional<Speaker> found = repository.findById(VALID_TEST_ID);

        // assert
        assertEquals(found.get().getSpeakerId(), VALID_TEST_ID);
    }
}
