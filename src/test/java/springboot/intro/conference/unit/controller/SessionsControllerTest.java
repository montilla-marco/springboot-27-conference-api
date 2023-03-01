package springboot.intro.conference.unit.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import springboot.intro.conference.controller.SessionsController;
import springboot.intro.conference.entity.Session;
import springboot.intro.conference.repository.SessionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SessionsControllerTest {

    @InjectMocks
    private SessionsController controller;

    @Mock
    private SessionRepository sessionRepository;

    @Test
    void givenNoSession_whenFindById_thenReturnEmpty() {
        // act
        Session result = controller.findById(1L);
        // assert
        assertThat(result).isNull();
    }

    @Test
    void giveOneSession_whenFindById_thenReturnNotEmpty() {
        // arrange
        Session session = new Session();
        session.setSessionId(1L);
        session.setSessionLength(45);
        session.setSessionDescription("Spring Boot first short intro");
        session.setSessionName("Spring Boot");
        when(sessionRepository.findById(anyLong())).thenReturn(Optional.of(session));

        // act
        Session result = controller.findById(1L);
        // assert
        assertThat(result).isEqualTo(session);
        assertThat(result.getSessionId()).isEqualTo(session.getSessionId());
        assertThat(result.getSessionDescription()).isEqualTo(session.getSessionDescription());
        assertThat(result.getSessionName()).isEqualTo(session.getSessionName());
        assertThat(result.getSessionLength()).isEqualTo(session.getSessionLength());
        assertThat(result.getSpeakers()).isNull();
    }

    @Test
    void giveNoSession_whenFindAll_thenReturnEmpty() {
        // arrange
        List<Session> expected = new ArrayList(0);
        when(sessionRepository.findAll()).thenReturn(expected);
        // act
        List<Session> result = controller.findAll();
        // assert
        assertThat(result).isNull();
    }

    @Test
    void giveOneSession_whenFindAll_thenReturnNotEmpty() {
        // arrange
        Session session = new Session();
        session.setSessionId(1L);
        session.setSessionLength(45);
        session.setSessionDescription("Spring Boot first short intro");
        session.setSessionName("Spring Boot");
        List<Session> expected = new ArrayList();
        expected.add(session);
        when(sessionRepository.findAll()).thenReturn(expected);

        // act
        List<Session> result = controller.findAll();
        // assert
        assertIterableEquals(expected, result);
        Session sessionResult = result.get(0);
        assertThat(sessionResult.getSessionId()).isEqualTo(session.getSessionId());
        assertThat(sessionResult.getSessionDescription()).isEqualTo(session.getSessionDescription());
        assertThat(sessionResult.getSessionName()).isEqualTo(session.getSessionName());
        assertThat(sessionResult.getSessionLength()).isEqualTo(session.getSessionLength());
        assertThat(sessionResult.getSpeakers()).isNull();
    }

    @Test
    void givenNewSession_whenSave_thenReturnSession() {
        // arrange
        Session session = new Session();
        session.setSessionLength(45);
        session.setSessionDescription("Spring Boot first short intro");
        session.setSessionName("Spring Boot");
        when(sessionRepository.save(any(Session.class))).thenReturn(session);

        // act
        Session sessionResult = controller.save(session);

        // assert
        assertThat(sessionResult.getSessionId()).isEqualTo(session.getSessionId());
        assertThat(sessionResult.getSessionDescription()).isEqualTo(session.getSessionDescription());
        assertThat(sessionResult.getSessionName()).isEqualTo(session.getSessionName());
        assertThat(sessionResult.getSessionLength()).isEqualTo(session.getSessionLength());
        assertThat(sessionResult.getSpeakers()).isNull();
    }
}
