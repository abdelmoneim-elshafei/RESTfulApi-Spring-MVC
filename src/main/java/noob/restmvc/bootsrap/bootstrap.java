package noob.restmvc.bootsrap;

import lombok.RequiredArgsConstructor;
import noob.restmvc.entity.Book;
import noob.restmvc.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class bootstrap implements CommandLineRunner {
    private final BookRepository bookRepository;
    @Override
    public void run(String... args) throws Exception {
        loadBookData();
    }
    public void loadBookData(){
        Book book1 = Book.builder()
                .title("To Kill a Mockingbird")
                .isbn("9780061120084")
                .author("Harper Lee")
                .price(BigDecimal.valueOf(9.99))
                .quantityOnHand(5)
                .build();

        Book book2 = Book.builder()
                .title("1984")
                .isbn("9780451524935")
                .author("George Orwell")
                .price(BigDecimal.valueOf(12.99))
                .quantityOnHand(8)
                .build();

        Book book3 = Book.builder()
                .title("Pride and Prejudice")
                .isbn("9780141439518")
                .author("Jane Austen")
                .price(BigDecimal.valueOf(7.99))
                .quantityOnHand(3)
                .build();

        Book book4 = Book.builder()
                .title("The Great Gatsby")
                .isbn("9780743273565")
                .author("F. Scott Fitzgerald")
                .price(BigDecimal.valueOf(10.99))
                .quantityOnHand(6)
                .build();

        Book book5 = Book.builder()
                .title("To the Lighthouse")
                .isbn("9780156907392")
                .author("Virginia Woolf")
                .price(BigDecimal.valueOf(8.99))
                .quantityOnHand(2)
                .build();

        Book book6 = Book.builder()
                .title("Moby-Dick")
                .isbn("9780142437247")
                .author("Herman Melville")
                .price(BigDecimal.valueOf(11.99))
                .quantityOnHand(4)
                .build();

        Book book7 = Book.builder()
                .title("The Catcher in the Rye")
                .isbn("9780316769488")
                .author("J.D. Salinger")
                .price(BigDecimal.valueOf(9.99))
                .quantityOnHand(7)
                .build();

        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
        bookRepository.save(book4);
        bookRepository.save(book5);
        bookRepository.save(book6);
        bookRepository.save(book7);
    }
}
