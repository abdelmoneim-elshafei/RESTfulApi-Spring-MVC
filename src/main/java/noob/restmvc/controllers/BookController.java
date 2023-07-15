package noob.restmvc.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noob.restmvc.model.BookDTO;
import noob.restmvc.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BookController {
   public static final String BOOK_PATH = "/api/v1/books";
   public static final String BOOK_PATH_ID = BOOK_PATH + "/{bookId}";
   private final BookService bookService;

   @GetMapping(BOOK_PATH)
   Page<BookDTO> getAllBooks(@RequestParam(required = false) String title,
                             @RequestParam(required = false) String isbn,
                             @RequestParam(required = false) Integer pageNumber,
                             @RequestParam(required = false) Integer  pageSize){
      return bookService.getAllBooks(title, isbn, pageNumber, pageSize);
   }

   @GetMapping(BOOK_PATH_ID)
   BookDTO getBookById(@PathVariable("bookId") UUID id){
      log.debug("Hi from Controller");
         return bookService.getBookById(id).get();
   }

   @PostMapping(BOOK_PATH)
   ResponseEntity<?> createNewBook(@Validated @RequestBody BookDTO bookDTO){
      BookDTO savedBookDTO = bookService.creeateNewBook(bookDTO);
      HttpHeaders headers = new HttpHeaders();
      headers.add("Location",BOOK_PATH + "/" + savedBookDTO.getId());
      return new ResponseEntity<>(savedBookDTO,headers,HttpStatus.CREATED);
   }

   @PutMapping(BOOK_PATH_ID)
   ResponseEntity<?> updateBookById(@PathVariable("bookId") UUID id, @RequestBody BookDTO bookDTO){
      bookService.updateBookById(id, bookDTO);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }

   @DeleteMapping(BOOK_PATH_ID)
   ResponseEntity<?> deleteBookById(@PathVariable("bookId") UUID id){
      bookService.deleteBookById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }

   @PatchMapping(BOOK_PATH_ID)
   ResponseEntity<?> updateBookByIdByPatchMethod(@PathVariable("bookId") UUID id, @RequestBody BookDTO bookDTO){
      bookService.updateBookByIdByPatchMethod(id, bookDTO);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }


}
