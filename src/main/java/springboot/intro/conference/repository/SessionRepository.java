package springboot.intro.conference.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.intro.conference.entity.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
