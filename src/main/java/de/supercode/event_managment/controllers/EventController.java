package de.supercode.event_managment.controllers;

import de.supercode.event_managment.entities.Event;
import de.supercode.event_managment.entities.Participant;
import de.supercode.event_managment.services.EventService;
import de.supercode.event_managment.services.ParticipantService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private ParticipantService participantService;

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        try {
            List<Event> events = eventService.getAllEvents();
            return ResponseEntity.ok(events);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); //  500 Internal Server Error
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Event>> getEventById(@PathVariable Long id) {
        Optional<Event> event = eventService.getEventById(id);
        if (event != null) {
            return ResponseEntity.ok(event);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        try {
            Event createdEvent = eventService.createEvent(event);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent); //  201 Created
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); //  400 Bad Request
        }
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Event> updateEvent(
            @PathVariable Long eventId,
            @RequestBody Event eventDetails) {
        Optional<Event> updatedEvent = eventService.updateEvent(eventId, eventDetails);
        if (updatedEvent.isPresent()) {
            return ResponseEntity.ok(updatedEvent.get());
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        if (eventService.deleteEvent(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{eventId}/participants")
    public ResponseEntity<Event> addParticipantToEvent(@PathVariable Long eventId, @RequestBody Participant participant) {
        Optional<Event> event = Optional.ofNullable(eventService.addParticipantToEvent(eventId, participant));
        return event.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{eventId}/participants")
    public ResponseEntity<List<Participant>> getParticipantsByEvent(@PathVariable Long eventId) {
        List<Participant> participants = eventService.getParticipantsByEvent(eventId);
        return ResponseEntity.ok(participants);
    }

    @PutMapping("/{eventId}/participants/{participantId}")
    public ResponseEntity<Participant> updateParticipantConfirmation(
            @PathVariable Long eventId,
            @PathVariable Long participantId,
            @RequestBody Boolean confirmed) {
        try {
            Optional<Participant> participant = eventService.updateParticipantConfirmation(eventId, participantId, confirmed);
            return participant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @DeleteMapping("/{eventId}/participants/{participantId}")
    public ResponseEntity<Void> removeParticipantFromEvent(@PathVariable Long eventId, @PathVariable Long participantId) {
        Optional<Event> event = participantService.removeParticipantFromEvent(eventId, participantId);
        if (event.isPresent()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}




