package de.supercode.event_managment.controllers;
import de.supercode.event_managment.entities.Participant;
import de.supercode.event_managment.services.EventService;
import de.supercode.event_managment.services.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/participants")
public class ParticipantController {
    @Autowired
    private ParticipantService participantService;


    @GetMapping
    public List<Participant> getAllParticipants() {
        return participantService.getAllParticipants();
    }
    @GetMapping("/{participantId}")
    public ResponseEntity<Participant> getParticipantById(@PathVariable Long participantId) {
        Optional<Participant> participant = participantService.getParticipantById(participantId);
        return participant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Participant createParticipant(@RequestBody Participant participant) {
        return participantService.createParticipant(participant);
    }

    @PutMapping("/{participantId}")
    public ResponseEntity<Participant> updateParticipant(@PathVariable Long participantId, @RequestBody Participant participantDetails) {
        Optional<Participant> updatedParticipant = participantService.updateParticipant(participantId, participantDetails);
        return updatedParticipant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{participantId}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable Long participantId) {
        if (participantService.deleteParticipant(participantId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
