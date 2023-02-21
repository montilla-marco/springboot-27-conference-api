package springboot.intro.conference.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "speakers")
public class Speaker {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "speaker_id")
    private Long speakerId;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "title")
    private String title;
    @Column(name = "company")
    private String company;
    @Column(name = "speaker_bio")
    private String speakerBio;

    @Lob
    @Column(name = "speaker_photo", columnDefinition = "BLOB")
    private byte[] speakerPhoto;

    @ManyToMany(mappedBy = "speakers")
    @JsonIgnore
    private List<Session> sessions;

    public Speaker() {
    }

    public Long getSpeakerId() {
        return speakerId;
    }

    public void setSpeakerId(Long speakerId) {
        this.speakerId = speakerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSpeakerBio() {
        return speakerBio;
    }

    public void setSpeakerBio(String speakerBio) {
        this.speakerBio = speakerBio;
    }

    public byte[] getSpeakerPhoto() {
        return speakerPhoto;
    }

    public void setSpeakerPhoto(byte[] speakerPhoto) {
        this.speakerPhoto = speakerPhoto;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }
}
