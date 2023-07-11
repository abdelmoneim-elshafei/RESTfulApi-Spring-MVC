package noob.restmvc.services;

import noob.restmvc.model.BookDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookService {
    List<BookDTO> getAllBooks();

    Optional<BookDTO> getBookById(UUID id);

    BookDTO creeateNewBook(BookDTO bookDTO);

    void updateBookById(UUID id, BookDTO bookDTO);

    void deleteBookById(UUID id);

    void updateBookByIdByPatchMethod(UUID id, BookDTO bookDTO);
}
