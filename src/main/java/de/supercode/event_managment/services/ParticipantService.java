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
public class ParticipantService {
    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private EventRepository eventRepository;

    public Optional<Participant> updateParticipantConfirmation(Long participantId, Boolean confirmed) {
        return participantRepository.findById(participantId).map(participant -> {
            participant.setConfirmed(confirmed);
            return participantRepository.save(participant);
        });
    }

    public Optional<Event> removeParticipantFromEvent(Long eventId, Long participantId) {
        Optional<Event> eventOpt = eventRepository.findById(eventId);
        if (eventOpt.isPresent()) {
            Event event = eventOpt.get();

            Participant participant = participantRepository.findById(participantId).orElse(null);
            if (participant != null && event.getParticipants().remove(participant)) {

                eventRepository.save(event);
                return Optional.of(event);
            }
        }
        return Optional.empty();
    }


    public List<Participant> getAllParticipants() {
        return participantRepository.findAll();
    }
    public Optional<Participant> getParticipantById(Long participantId) {
        return participantRepository.findById(participantId);
    }

    public Participant createParticipant(Participant participant) {
        return participantRepository.save(participant);
    }
    public Optional<Participant> updateParticipant(Long participantId, Participant participantDetails) {
        return participantRepository.findById(participantId).map(participant -> {
            participant.setName(participantDetails.getName());
            participant.setEmail(participantDetails.getEmail());
            participant.setConfirmed(participantDetails.getConfirmed());
            return participantRepository.save(participant);
        });
    }
    public boolean deleteParticipant(Long participantId) {
        if (participantRepository.existsById(participantId)) {
            participantRepository.deleteById(participantId);
            return true;
        }
        return false;
    }
}
