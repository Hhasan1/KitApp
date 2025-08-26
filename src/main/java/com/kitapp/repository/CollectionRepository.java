package com.kitapp.repository;

import com.kitapp.entity.Collection;
import com.kitapp.entity.User;
import com.kitapp.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {
    List<Collection> findByUser(User user);
    Optional<Collection> findByUserAndBook(User user, Book book);
}
