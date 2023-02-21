package springboot.intro.conference.integration.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import springboot.intro.conference.entity.Session;
import springboot.intro.conference.repository.SessionRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
    void givenNullId_whenFindById_thenThrownInvalidDataAccessApiUsageException() {
        // arrange
        String expectedMessage = "The given id must not be null!";

        // assert
        Exception exception = assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            // act
            Optional<Session> found = repository.findById(NULL_ID);
        });

        // assert
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void givenInvalidId_whenFindById_thenReturnEmptySession() {
        // act
        Optional<Session> found = repository.findById(INVALID_ID);

        // asert
        assertFalse(found.isPresent());
    }

    @Test
    void givenValidId_whenFindById_thenReturnSession() {
        // act
        Optional<Session> found = repository.findById(VALID_ID);

        // assert
        assertEquals(found.get().getSessionId(), VALID_ID);
    }

    @Test
    void whenFindAll_thenReturnNotEmptySessions() {
        // act
        List<Session> found = repository.findAll();

        // asert
        assertTrue(found.size() > 0);
    }

    //todo database versioning and preparing data for test
    @Test
    void whenFindAll_thenReturnEmptySessions() { }

    @Test
    void whenCreateANullEntity_thenThrownInvalidDataAccessApiUsageException() {
        // arrange
        String expectedMessage = "Entity must not be null.";

        // assert
        Exception exception = assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            // act
            repository.saveAndFlush(null);
        });

        // assert
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void whenCreateAEmptyEntity_thenThrownInvalidDataAccessApiUsageException() {
        // assert
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
            // act
            repository.saveAndFlush(new Session());
        });
    }

    @Test
    void whenCreateAValidEntity_thenReturnNotEmptySession() {
        // arrange
        Session session = new Session();
        session.setSessionLength(45);
        session.setSessionDescription("Spring Boot first short intro");
        session.setSessionName("Spring Boot");

        // act
        Session result = repository.save(session);

        // assert
        assertThat(result).isNotNull();
        assertThat(result.getSessionDescription()).isEqualTo(session.getSessionDescription());
        assertThat(result.getSessionName()).isEqualTo(session.getSessionName());
        assertThat(result.getSessionLength()).isEqualTo(session.getSessionLength());
        assertThat(result.getSpeakers()).isNull();
    }
}
