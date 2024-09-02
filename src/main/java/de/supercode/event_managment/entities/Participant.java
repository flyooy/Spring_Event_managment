package de.supercode.event_managment.entities;
import de.supercode.event_managment.entities.Event;
import jakarta.persistence.*;

@Entity
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private Boolean confirmed;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    public Participant() {
    }
    public Participant(String name, String email, Boolean confirmed) {
        this.name = name;
        this.email = email;
        this.confirmed = confirmed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

}
