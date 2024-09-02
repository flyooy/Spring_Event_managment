package de.supercode.event_managment.repository;

import de.supercode.event_managment.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
