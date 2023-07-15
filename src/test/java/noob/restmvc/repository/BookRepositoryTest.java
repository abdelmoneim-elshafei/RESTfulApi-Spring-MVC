package noob.restmvc.repository;

import jakarta.validation.ConstraintViolationException;
import noob.restmvc.bootsrap.bootstrap;
import noob.restmvc.entity.Book;
import noob.restmvc.services.BookCSVServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({bootstrap.class, BookCSVServiceImpl.class})
class BookRepositoryTest {
    
    @Autowired
    BookRepository bookRepository;

    @Test
    void getBookByTitle() {
        Page<Book> list = bookRepository.findBookByTitleLikeIgnoreCase("Chasm City", null);
        assertThat(list.getContent().size()).isEqualTo(1);
    }
    @Test
    void getBookByIsbn() {
        Page<Book> list = bookRepository.findBookByIsbn("097915930X", null);
        assertThat(list.getContent().size()).isEqualTo(1);
    }
    @Test
    void saveBookTooLong() {
        assertThrows(ConstraintViolationException.class,()->{
            Book savedBook = bookRepository.save(Book.builder()
                    .title("Loot the tresLoot the tresLoot the tresLoot the tresLoot the tresLoot the tresLoot the tresLoot the tresLoot the tresLoot the tresLoot the tresLoot the tresLoot the tresLoot the tresLoot the tresLoot the tresLoot the tresLoot the tresLoot the tresLoot the tresLoot the tresLoot the tres")
                    .author("abdo elbalf")
                    .isbn("383-88848484")
                    .build());

            bookRepository.flush();
            assertThat(savedBook).isNotNull();
            assertThat(savedBook.getId()).isNotNull();
        });

    }
    @Test
    void saveBook() {
        Book savedBook = bookRepository.save(Book.builder()
                        .title("Loot the tres")
                        .author("abdo elbalf")
                        .isbn("383-88848484")
                        .build());

        bookRepository.flush();
        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getId()).isNotNull();
    }
}