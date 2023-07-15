package noob.restmvc.bootsrap;

import lombok.RequiredArgsConstructor;
import noob.restmvc.entity.Book;
import noob.restmvc.entity.Customer;
import noob.restmvc.model.BookCSVRecord;
import noob.restmvc.repository.BookRepository;
import noob.restmvc.repository.CustomerRepository;
import noob.restmvc.services.BookCSVService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class bootstrap implements CommandLineRunner {
    private final BookRepository bookRepository;
    private final BookCSVService bookCSVService;
    private final CustomerRepository customerRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        //loadBookData();
        loadBookFromCSV();
        loadCustomersData();

    }
    private void loadCustomersData(){
        Customer customer1 = Customer.builder()
                .name("John Doe")
                .email("johndoe@example.com")
                .build();

        Customer customer2 = Customer.builder()
                .name("Abdo Doen")
                .email("AbdoDoen@example.com")
                .build();

        Customer customer3 = Customer.builder()
                .name("Ahmed Eid")
                .email("AhmedEid@example.com")
                .build();


        // Save the customers to the repository
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);
    }

    private void loadBookFromCSV() throws FileNotFoundException {
        if (bookRepository.count() == 0) {

            File file = ResourceUtils.getFile("classpath:csvdata/books.csv");
            List<BookCSVRecord> list = bookCSVService.convertCSV(file);

            list.forEach(bookCSVRecord -> {
                if (StringUtils.hasText(bookCSVRecord.getIsbn()) &&
                        StringUtils.hasText(bookCSVRecord.getTitle()) &&
                        StringUtils.hasText(bookCSVRecord.getAuthor())) {
                    bookRepository.save(Book.builder()
                            .isbn(bookCSVRecord.getIsbn())
                            .author(org.apache.commons.lang3.StringUtils.abbreviate(bookCSVRecord.getAuthor(), 250))
                            .title(org.apache.commons.lang3.StringUtils.abbreviate(bookCSVRecord.getTitle(), 50))
                            .price(BigDecimal.TEN)
                            .quantityOnHand(100 + (int) (Math.random() * ((500 - 100) + 1)))
                            .build());
                }

            });
        }
    }

    public void loadBookData() {
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
