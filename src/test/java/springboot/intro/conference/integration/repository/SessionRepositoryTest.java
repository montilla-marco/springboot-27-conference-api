package springboot.intro.conference.integration.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import springboot.intro.conference.entity.Session;
import springboot.intro.conference.repository.SessionRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class SessionRepositoryTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private SessionRepository repository;

    private final static Long VALID_ID = 2L;
    private final static Long INVALID_ID = 0L;
    private final static Long NULL_ID = null;

    @Test
    void givenNullId_whenFindById_thenThrownIllegalArgumentException() {
        // arrange
        String expectedMessage = "The given id must not be null!";

        Exception exception = assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            // act
            Optional<Session> found = repository.findById(NULL_ID);
        });

        // assert
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void givenNullId_whenFindById_thenReturnSession() {
        // act
        Optional<Session> found = repository.findById(INVALID_ID);

        // asert
        assertFalse(found.isPresent());
    }

    @Test
    void givenId_whenFindById_thenReturnSession() {
        // act
        Optional<Session> found = repository.findById(VALID_ID);

        // assert
        assertEquals(found.get().getSessionId(), VALID_ID);
    }
}
