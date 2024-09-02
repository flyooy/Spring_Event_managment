package de.supercode.event_managment.repository;

import de.supercode.event_managment.entities.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}