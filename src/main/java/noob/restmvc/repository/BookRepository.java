package noob.restmvc.repository;

import noob.restmvc.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book,UUID> {
    Page<Book> findBookByTitleLikeIgnoreCase(String title, Pageable pageable);
    Page<Book> findBookByIsbn(String isbn, Pageable pageable);
}
