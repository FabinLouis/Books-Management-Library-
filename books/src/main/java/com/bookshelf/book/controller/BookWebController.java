package com.bookshelf.book.controller;



import com.bookshelf.book.BookRepos.BookRepository;
import com.bookshelf.book.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/web/books")
public class BookWebController {

    @Autowired
    private BookRepository repository;

    // Show all books
    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", repository.findAll());
        return "books"; // maps to books.html
    }

    // Show form to add a book
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book());
        return "create-book"; // maps to create-book.html
    }

    // Handle create form submission
    @PostMapping
    public String createBook(@ModelAttribute Book book) {
        repository.save(book);
        return "redirect:/web/books";
    }

    // Show form to edit a book
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Book book = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        model.addAttribute("book", book);
        return "edit-book"; // maps to edit-book.html
    }

    // Handle edit form submission
    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute Book bookDetails) {
        Book book = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        repository.save(book);
        return "redirect:/web/books";
    }

    // Delete book
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/web/books";
    }
}
