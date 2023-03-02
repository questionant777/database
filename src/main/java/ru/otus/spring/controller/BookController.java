package ru.otus.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.domain.dto.BookDto;
import ru.otus.spring.service.BookService;

import java.util.List;

@RestController
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/book")
    public ResponseEntity<List<BookDto>> findAllInDto() {
        return ResponseEntity.ok(bookService.findAllInDto());
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookDto> getBookByIdInPath(@PathVariable("id") long id) {
        return ResponseEntity.ok(bookService.findByIdInDto(id));
    }

    @PostMapping("/book")
    public ResponseEntity<BookDto> insertBook(@RequestBody BookDto bookDto) {
        return ResponseEntity.ok(bookService.insert(bookDto));
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<BookDto> updateBook(
            @PathVariable("id") long id,
            @RequestBody BookDto bookDto)
    {
        bookDto.setId(id);
        return ResponseEntity.ok(bookService.update(bookDto));
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") long id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
