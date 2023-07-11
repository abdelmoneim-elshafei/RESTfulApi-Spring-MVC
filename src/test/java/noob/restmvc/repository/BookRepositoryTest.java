package noob.restmvc.repository;

import noob.restmvc.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {
    
    @Autowired
    BookRepository bookRepository;

    @Test
    void saveBook() {
        Book savedBook = bookRepository.save(Book.builder()
                        .title("Loot the tres")
                        .author("abdo elbalf")
                        .isbn("383-88848484")
                        .build());

        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getId()).isNotNull();
    }
}