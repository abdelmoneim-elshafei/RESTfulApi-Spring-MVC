package noob.restmvc.services;

import noob.restmvc.model.Book;

import java.util.List;
import java.util.UUID;

public interface BookService {
    List<Book> getAllBooks();

    Book getBookById(UUID id);

    Book creeateNewBook(Book book);

    void updateBookById(UUID id, Book book);

    void deleteBookById(UUID id);

    void updateBookByIdByPatchMethod(UUID id, Book book);
}
