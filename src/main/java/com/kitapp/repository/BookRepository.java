package com.kitapp.repository;

import com.kitapp.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    @Query("SELECT b FROM Book b WHERE " +
           "(:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
           "(:author IS NULL OR LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%'))) AND " +
           "(:subject IS NULL OR LOWER(b.subject) LIKE LOWER(CONCAT('%', :subject, '%'))) AND " +
           "(:language IS NULL OR LOWER(b.language) LIKE LOWER(CONCAT('%', :language, '%'))) AND " +
           "(:isbn IS NULL OR b.isbn = :isbn) AND " +
           "(:year IS NULL OR b.publicationYear = :year)")
    List<Book> searchBooks(@Param("title") String title,
                          @Param("author") String author,
                          @Param("subject") String subject,
                          @Param("language") String language,
                          @Param("isbn") String isbn,
                          @Param("year") Integer year);
    
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthorContainingIgnoreCase(String author);
}
