package com.kitapp.repository;

import com.kitapp.entity.Reservation;
import com.kitapp.entity.User;
import com.kitapp.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUser(User user);
    List<Reservation> findByBook(Book book);
    Optional<Reservation> findByUserAndBookAndStatus(User user, Book book, Reservation.Status status);
    List<Reservation> findByUserAndStatus(User user, Reservation.Status status);
}
