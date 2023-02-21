package springboot.intro.conference.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import springboot.intro.conference.controller.SessionsController;
import springboot.intro.conference.entity.Session;
import springboot.intro.conference.repository.SessionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SessionsController.class)
class SessionsAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessionRepository sessionRepository;

    @Test
    void givenNoSession_whenFindById_thenReturnEmptyBody() throws Exception {
        // act and assert
        this.mockMvc.perform(get("/api/v1/sessions/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.emptyString()));
    }

    @Test
    void giveOneSession_whenFindById_thenReturnNotEmptyBody() throws Exception {
        // arrange
        Session session = new Session();
        session.setSessionId(1L);
        session.setSessionLength(45);
        session.setSessionDescription("Spring Boot first short intro");
        session.setSessionName("Spring Boot");
        when(sessionRepository.findById(anyLong())).thenReturn(Optional.of(session));
        ObjectMapper mapper = new ObjectMapper();
        //Converting the Object to JSONString
        String expectedSessionJson = mapper.writeValueAsString(session);
        // act and assert
        this.mockMvc.perform(get("/api/v1/sessions/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedSessionJson));
    }

    @Test
    void givenNoSession_whenFindAll_thenReturnEmptyBody() throws Exception {
        // act and assert
        this.mockMvc.perform(get("/api/v1/sessions"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.emptyString()));
    }

    @Test
    void giveOneSession_whenFindAll_thenReturnNotEmptyBody() throws Exception {
        // arrange
        Session session = new Session();
        session.setSessionId(1L);
        session.setSessionLength(45);
        session.setSessionDescription("Spring Boot first short intro");
        session.setSessionName("Spring Boot");
        List<Session> expected = new ArrayList();
        expected.add(session);
        when(sessionRepository.findAll()).thenReturn(expected);

        ObjectMapper mapper = new ObjectMapper();
        //Converting the Object to JSONString
        String expectedSessionsJson = mapper.writeValueAsString(expected);

        // act and assert
        this.mockMvc.perform(get("/api/v1/sessions"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedSessionsJson));
    }

    @Test
    void givenNewSession_whenSave_thenReturnSession() throws Exception {
        // arrange
        Session session = new Session();
        session.setSessionLength(45);
        session.setSessionDescription("Spring Boot first short intro");
        session.setSessionName("Spring Boot");
        ObjectMapper mapper = new ObjectMapper();
        //Converting the Object to JSONString
        String sessionsJson = mapper.writeValueAsString(session);

        // act and assert
        this.mockMvc.perform(post("/api/v1/sessions")
                        .content(sessionsJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}
