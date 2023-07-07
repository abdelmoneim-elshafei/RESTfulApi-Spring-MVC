package noob.restmvc.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noob.restmvc.model.Book;
import noob.restmvc.services.BookService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/books")
public class BookController {
   private final BookService bookService;

   @GetMapping
   List<Book> getAllBooks(){
      return bookService.getAllBooks();
   }
   @GetMapping("{bookId}")
   Book getBookById(@PathVariable("bookId") UUID id){
      log.debug("Hi from Controller");
      return bookService.getBookById(id);
   }

   @PostMapping
   ResponseEntity<?> createNewBook(@RequestBody Book book){
      Book savedBook = bookService.creeateNewBook(book);
      HttpHeaders headers = new HttpHeaders();
      headers.add("Location","api/v1/books/" + savedBook.getId());
      return new ResponseEntity<>(savedBook,headers,HttpStatus.CREATED);
   }

   @PutMapping("{bookId}")
   ResponseEntity<?> updateBookById(@PathVariable("bookId") UUID id, @RequestBody Book book){
      bookService.updateBookById(id,book);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }

   @DeleteMapping("{bookId}")
   ResponseEntity<?> deleteBookById(@PathVariable("bookId") UUID id){
      bookService.deleteBookById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }

   @PatchMapping("{bookId}")
   ResponseEntity<?> updateBookByIdByPatchMethod(@PathVariable("bookId") UUID id, @RequestBody Book book){
      bookService.updateBookByIdByPatchMethod(id, book);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }


}
