package noob.restmvc.repository;

import noob.restmvc.entity.Book;
import noob.restmvc.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryRepositoryTest {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    BookRepository bookRepository;
    
    Book book;

    @BeforeEach
    void setUp() {
        book = bookRepository.findAll().get(0);
    }

    @Transactional
    @Test
    void manyToManyPersistence() {
        Category category = categoryRepository.save(Category.builder()
                        .description("Fiction").build());
        book.addCategory(category);
        Book savedBook = bookRepository.save(book);
        System.out.println(book);
    }
}