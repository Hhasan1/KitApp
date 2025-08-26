package com.kitapp.repository;

import com.kitapp.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    
    @Query("SELECT e FROM Event e WHERE e.eventDate >= :now ORDER BY e.eventDate ASC")
    List<Event> findUpcomingEvents(LocalDateTime now);
    
    List<Event> findByEventDateAfterOrderByEventDateAsc(LocalDateTime date);
}

