package noob.restmvc.services;

import noob.restmvc.model.BookDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface BookService {
    Page<BookDTO> getAllBooks(String title, String isbn, Integer pageNumber, Integer PageSize);

    Optional<BookDTO> getBookById(UUID id);

    BookDTO creeateNewBook(BookDTO bookDTO);

    void updateBookById(UUID id, BookDTO bookDTO);

    void deleteBookById(UUID id);

    void updateBookByIdByPatchMethod(UUID id, BookDTO bookDTO);
}
