package springboot.intro.conference.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import springboot.intro.conference.controller.SessionsController;
import springboot.intro.conference.entity.Session;
import springboot.intro.conference.repository.SessionRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SessionsControllerTest {

    @InjectMocks
    private SessionsController controller;

    @Mock
    private SessionRepository sessionRepository;

    @Test
    void givenNoSession_whenGetById_thenReturnEmpty() {
        // act
        Session result = controller.findById(1L);
        // assert
        assertThat(result).isEqualTo(null);
    }

    @Test
    void giveOneSession_whenGetAllSession_thenReturnNotEmpty() {
        // arrange
        Session session = new Session();
        session.setSessionId(1L);
        session.setSessionLength(45);
        session.setSessionDescription("Spring Boot first short intro");
        session.setSessionName("Spring Boot");
        session.setSpeakers(null);
        when(sessionRepository.findById(anyLong())).thenReturn(Optional.of(session));

        // act
        Session result = controller.findById(1L);
        // assert
        assertThat(result).isEqualTo(session);
        assertThat(result.getSessionId()).isEqualTo(session.getSessionId());
        assertThat(result.getSessionDescription()).isEqualTo(session.getSessionDescription());
        assertThat(result.getSessionName()).isEqualTo(session.getSessionName());
        assertThat(result.getSessionLength()).isEqualTo(session.getSessionLength());
        assertThat(result.getSpeakers()).isEqualTo(null);
    }

}
