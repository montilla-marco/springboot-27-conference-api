package springboot.intro.conference.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.intro.conference.entity.Speaker;

public interface SpeakerRepository extends JpaRepository<Speaker, Long> {
}
