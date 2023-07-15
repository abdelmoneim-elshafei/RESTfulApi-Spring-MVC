package noob.restmvc.repository;

import noob.restmvc.entity.Book;
import noob.restmvc.entity.BookOrder;
import noob.restmvc.entity.BookOrderShipment;
import noob.restmvc.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookOrderRepositoryTest {
    
    @Autowired
    BookOrderRepository bookOrderRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    BookRepository bookRepository;

    Customer customer;
    Book book;

    @BeforeEach
    void setUp() {
        customer = customerRepository.findAll().get(0);
        book = bookRepository.findAll().get(0);
    }


    @Transactional
    @Test
    void testBookOrder() {
        BookOrder bookOrder = BookOrder.builder()
                .customerRef("Test")
                .customer(customer)
                .bookOrderShipment(BookOrderShipment.builder()
                        .trackingNumber("1003434")
                        .build())
                .build();
      BookOrder savedBookOrder = bookOrderRepository.save(bookOrder);
      System.out.println(savedBookOrder);

    }
}