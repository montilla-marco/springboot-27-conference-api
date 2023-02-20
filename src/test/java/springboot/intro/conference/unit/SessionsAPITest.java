package springboot.intro.conference.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import springboot.intro.conference.controller.SessionsController;
import springboot.intro.conference.entity.Session;
import springboot.intro.conference.repository.SessionRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(SessionsController.class)
class SessionsAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessionRepository sessionRepository;

    @Test
    void givenNoSession_whenGetById_thenReturnEmptyBody() throws Exception {
        // act and assert
        this.mockMvc.perform(get("/api/v1/sessions/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.emptyString()));
    }

    @Test
    void giveOneSession_whenGetAllSession_thenReturnNotEmptyBody() throws Exception {
        // arrange
        Session session = new Session();
        session.setSessionId(1L);
        session.setSessionLength(45);
        session.setSessionDescription("Spring Boot first short intro");
        session.setSessionName("Spring Boot");
        session.setSpeakers(null);
        when(sessionRepository.findById(anyLong())).thenReturn(Optional.of(session));
        ObjectMapper mapper = new ObjectMapper();
        //Converting the Object to JSONString
        String expectedSessionJson = mapper.writeValueAsString(session);

        this.mockMvc.perform(get("/api/v1/sessions/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedSessionJson));
    }

}
