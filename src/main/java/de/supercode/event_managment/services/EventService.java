package de.supercode.event_managment.services;

import de.supercode.event_managment.entities.Event;
import de.supercode.event_managment.entities.Participant;
import de.supercode.event_managment.repository.EventRepository;
import de.supercode.event_managment.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long eventId) {
        return eventRepository.findById(eventId);
    }

    public Optional<Event> updateEvent(Long eventId, Event eventDetails) {
        return eventRepository.findById(eventId).map(event -> {
            event.setName(eventDetails.getName());
            event.setDate(eventDetails.getDate());
            event.setLocation(eventDetails.getLocation());
            event.setParticipants(eventDetails.getParticipants());
            return eventRepository.save(event);
        });
    }

    public boolean deleteEvent(Long eventId) {
        if (eventRepository.existsById(eventId)) {
            eventRepository.deleteById(eventId);
            return true;
        }
        return false;
    }

    public Optional<Participant> updateParticipantConfirmation(Long eventId, Long participantId, Boolean confirmed) {
        return participantRepository.findById(participantId).map(participant -> {
            if (participant != null) {
                participant.setConfirmed(confirmed);
                participantRepository.save(participant);
                return participant;
            } else {
                return null;
            }
        });
    }
    public List<Participant> getParticipantsByEvent(Long eventId) {
        return eventRepository.findById(eventId).map(Event::getParticipants).orElse(List.of());
    }

    public Event addParticipantToEvent(Long eventId, Participant participant) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow();
        event.getParticipants().add(participant);
        participantRepository.save(participant);
        return eventRepository.save(event);
    }
}

