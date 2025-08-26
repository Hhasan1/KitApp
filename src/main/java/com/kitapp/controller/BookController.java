package com.kitapp.controller;

import com.kitapp.entity.Book;
import com.kitapp.entity.User;
import com.kitapp.service.BookService;
import com.kitapp.service.UserService;
import com.kitapp.dto.BookSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @GetMapping("/books")
    public String books(Model model, @ModelAttribute BookSearchRequest searchRequest) {
        if (searchRequest.getTitle() != null || searchRequest.getAuthor() != null || 
            searchRequest.getSubject() != null || searchRequest.getLanguage() != null || 
            searchRequest.getIsbn() != null || searchRequest.getPublicationYear() != null) {
            model.addAttribute("books", bookService.searchBooks(searchRequest));
        } else {
            model.addAttribute("books", bookService.getAllBooks());
        }
        model.addAttribute("searchRequest", searchRequest);
        return "books";
    }

    @GetMapping("/book/{id}")
    public String bookDetail(@PathVariable Long id, Model model) {
        Optional<Book> book = bookService.findById(id);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            return "book-detail";
        }
        return "redirect:/books";
    }

    @PostMapping("/api/books/reserve")
    @ResponseBody
    public String reserveBook(@RequestParam Long bookId, Authentication authentication,
                             RedirectAttributes redirectAttributes) {
        if (authentication == null) {
            return "{\"success\": false, \"message\": \"Please log in to reserve books\"}";
        }

        try {
            Optional<User> userOpt = userService.findByUsername(authentication.getName());
            if (userOpt.isPresent()) {
                bookService.reserveBook(userOpt.get(), bookId);
                return "{\"success\": true, \"message\": \"Book reserved successfully!\"}";
            }
            return "{\"success\": false, \"message\": \"User not found\"}";
        } catch (Exception e) {
            return "{\"success\": false, \"message\": \"" + e.getMessage() + "\"}";
        }
    }

    @PostMapping("/api/books/collection")
    @ResponseBody
    public String addToCollection(@RequestParam Long bookId, Authentication authentication) {
        if (authentication == null) {
            return "{\"success\": false, \"message\": \"Please log in to add books to collection\"}";
        }

        try {
            Optional<User> userOpt = userService.findByUsername(authentication.getName());
            if (userOpt.isPresent()) {
                bookService.addToCollection(userOpt.get(), bookId);
                return "{\"success\": true, \"message\": \"Book added to collection!\"}";
            }
            return "{\"success\": false, \"message\": \"User not found\"}";
        } catch (Exception e) {
            return "{\"success\": false, \"message\": \"" + e.getMessage() + "\"}";
        }
    }
}
