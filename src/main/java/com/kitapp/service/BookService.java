package com.kitapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kitapp.dto.BookSearchRequest;
import com.kitapp.entity.Book;
import com.kitapp.entity.Collection;
import com.kitapp.entity.Reservation;
import com.kitapp.entity.User;
import com.kitapp.repository.BookRepository;
import com.kitapp.repository.CollectionRepository;
import com.kitapp.repository.ReservationRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CollectionRepository collectionRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public List<Book> searchBooks(BookSearchRequest request) {
        return bookRepository.searchBooks(
            request.getTitle(),
            request.getAuthor(),
            request.getSubject(),
            request.getLanguage(),
            request.getIsbn(),
            request.getPublicationYear()
        );
    }

    public boolean reserveBook(User user, Long bookId) throws Exception {
        Optional<Book> bookOpt = bookRepository.findById(bookId);
        if (!bookOpt.isPresent()) {
            throw new Exception("Book not found");
        }

        Book book = bookOpt.get();
        if (!book.isAvailable()) {
            throw new Exception("Book is not available");
        }

        Optional<Reservation> existingReservation = reservationRepository
            .findByUserAndBookAndStatus(user, book, Reservation.Status.ACTIVE);
        if (existingReservation.isPresent()) {
            throw new Exception("You already have this book reserved");
        }

        Reservation reservation = new Reservation(user, book);
        reservationRepository.save(reservation);
        
        book.setCopies(book.getCopies() - 1);
        bookRepository.save(book);
        
        return true;
    }

    public boolean addToCollection(User user, Long bookId) throws Exception {
        Optional<Book> bookOpt = bookRepository.findById(bookId);
        if (!bookOpt.isPresent()) {
            throw new Exception("Book not found");
        }

        Book book = bookOpt.get();
        
        Optional<Collection> existingCollection = collectionRepository.findByUserAndBook(user, book);
        if (existingCollection.isPresent()) {
            throw new Exception("Book is already in your collection");
        }

        Collection collection = new Collection(user, book);
        collectionRepository.save(collection);
        
        return true;
    }

    public List<Reservation> getUserReservations(User user) {
        return reservationRepository.findByUserAndStatus(user, Reservation.Status.ACTIVE);
    }

    public List<Collection> getUserCollection(User user) {
        return collectionRepository.findByUser(user);
    }
}