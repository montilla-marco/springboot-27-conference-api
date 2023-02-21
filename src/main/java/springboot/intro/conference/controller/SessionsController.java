package springboot.intro.conference.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import springboot.intro.conference.entity.Session;
import springboot.intro.conference.repository.SessionRepository;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/v1/sessions")
public class SessionsController {

    @Autowired
    private SessionRepository sessionRepository;

    @GetMapping(value = "/{id}")
    public Session findById(@PathVariable(name = "id", required = true) Long id) {
        Optional<Session> session = sessionRepository.findById(id);
        return session.isPresent() ? session.get() : null;
    }

    @GetMapping
    public List<Session> findAll() {
        List<Session> sessions = sessionRepository.findAll();
        return sessions.isEmpty() ? null : sessions;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Session save(@RequestBody(required = true) Session session) {
        return sessionRepository.save(session);
    }
}
