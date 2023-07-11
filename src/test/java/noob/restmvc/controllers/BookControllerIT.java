package noob.restmvc.controllers;

import noob.restmvc.entity.Book;
import noob.restmvc.exception.NotFoundException;
import noob.restmvc.mapper.BookMapper;
import noob.restmvc.model.BookDTO;
import noob.restmvc.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class BookControllerIT {
    @Autowired
    BookController controller;

    @Autowired
    BookRepository repository;

    @Autowired
    BookMapper mapper;


    @Test
    void deleteByIdNotFound() {
        assertThrows(NotFoundException.class, ()->{
            controller.deleteBookById(UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void deleteById(){
        Book book = repository.findAll().get(0);
        ResponseEntity<?> re = controller.deleteBookById(book.getId());
        assertThat(re.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        assertThat(repository.findById(book.getId())).isEmpty();

    }

    @Test
    void updateByIdNotFound(){
        assertThrows(NotFoundException.class, () ->{
          controller.updateBookById(UUID.randomUUID(),BookDTO.builder().build());
        });
    }

    @Test
    void updateById() {
        Book book = repository.findAll().get(0);
        BookDTO bookDTO = mapper.bookToBookDTO(book);
        bookDTO.setId(null);
        bookDTO.setVersion(null);
        String newName = "New Title";
        bookDTO.setTitle(newName);
        ResponseEntity<?> re = controller.updateBookById(book.getId(),bookDTO);
        assertThat(re.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Book update = repository.findById(book.getId()).get();
        assertThat(update.getTitle()).isEqualTo(newName);
    }

    @Rollback
    @Transactional
    @Test
    void createNewBook(){
        BookDTO bookDTO = BookDTO.builder()
                .title("New Book")
                .build();

        ResponseEntity<?> responseEntity = controller.createNewBook(bookDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();
        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID uuid = UUID.fromString(locationUUID[4]);
        assertThat(repository.findById(uuid).get().getTitle()).isEqualTo(bookDTO.getTitle());
    }

    @Test
    void getBookByIdNotFound(){
        assertThrows(NotFoundException.class, ()->{
            controller.getBookById(UUID.randomUUID());
        });
    }

    @Test
    void getBookById(){
        Book book = repository.findAll().get(0);
        BookDTO bookDTO = controller.getBookById(book.getId());
        assertThat(book.getTitle()).isEqualTo(bookDTO.getTitle());
    }

    @Test
    void testGetAllBooks(){
        List<BookDTO> list  = controller.getAllBooks();
        assertThat(list.size()).isEqualTo(7);
    }

    @Rollback
    @Transactional
    @Test
    void testGetAllBooksEmpty(){
        repository.deleteAll();
        List<BookDTO> list  = controller.getAllBooks();
        assertThat(list.size()).isEqualTo(0);
    }


}