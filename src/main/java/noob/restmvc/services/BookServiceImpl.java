package noob.restmvc.services;

import lombok.extern.slf4j.Slf4j;
import noob.restmvc.model.Book;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BookServiceImpl implements BookService {
    Map<UUID,Book> booksMap;

    public BookServiceImpl() {
        booksMap = new HashMap<>();
        Book book1 = Book.builder()
               .id(UUID.randomUUID())
               .version(1)
               .title("Java How To")
               .author("Bob Marl")
               .isbn("343-3434343")
               .quantityOnHand(300)
               .createdData(LocalDateTime.now())
               .updatedDate(LocalDateTime.now())
               .price(new BigDecimal("19.34"))
               .build();
        Book book2 = Book.builder()
                .id(UUID.randomUUID())
                .version(1)
                .title("Data Structure And Algorithms ")
                .author("Dan Hook")
                .isbn("348-8885747")
                .quantityOnHand(218)
                .createdData(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .price(new BigDecimal("13.44"))
                .build();

        Book book3 = Book.builder()
                .id(UUID.randomUUID())
                .version(1)
                .title("C++ How To")
                .author("Ahmed Dome")
                .isbn("343-858433")
                .quantityOnHand(130)
                .createdData(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .price(new BigDecimal("17.24"))
                .build();

        booksMap.put(book1.getId(),book1);
        booksMap.put(book2.getId(),book2);
        booksMap.put(book3.getId(),book3);
    }

    @Override
    public List<Book> getAllBooks(){
        return new ArrayList<>(booksMap.values());
    }
    @Override
    public Book getBookById(UUID id) {
        log.debug("Hello From Service" + id);
        return booksMap.get(id);
    }

    @Override
    public Book creeateNewBook(Book book) {
        Book newBook = Book.builder()
                .id(UUID.randomUUID())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .quantityOnHand(book.getQuantityOnHand())
                .author(book.getAuthor())
                .price(book.getPrice())
                .createdData(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .version(1)
                .build();
        booksMap.put(newBook.getId(),newBook);
        return booksMap.get(newBook.getId());
    }

    @Override
    public void updateBookById(UUID id, Book book) {
        Book existBook = booksMap.get(id);
        existBook.setAuthor(book.getAuthor());
        existBook.setTitle(book.getTitle());
        existBook.setPrice(book.getPrice());
        existBook.setQuantityOnHand(book.getQuantityOnHand());
        existBook.setIsbn(book.getIsbn());

        booksMap.put(existBook.getId(),existBook);
    }

    @Override
    public void deleteBookById(UUID id) {
       booksMap.remove(id);
    }

    @Override
    public void updateBookByIdByPatchMethod(UUID id, Book book) {
        Book existBook = booksMap.get(id);

        if(StringUtils.hasText(existBook.getTitle())){
           existBook.setTitle(book.getTitle());
        }
        if(StringUtils.hasText(existBook.getIsbn())){
            existBook.setIsbn(book.getIsbn());
        }
        if(existBook.getPrice() != null){
            existBook.setPrice(book.getPrice());
        }
        if(existBook.getQuantityOnHand() != null){
            existBook.setQuantityOnHand(book.getQuantityOnHand());
        }
        if(StringUtils.hasText(existBook.getAuthor())){
            existBook.setAuthor(book.getAuthor());
        }

        booksMap.put(existBook.getId(),existBook);
    }
}
