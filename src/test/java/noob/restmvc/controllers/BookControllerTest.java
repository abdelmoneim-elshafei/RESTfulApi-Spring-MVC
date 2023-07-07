package noob.restmvc.controllers;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
class BookControllerTest {

    @Autowired
    BookController bookController;

    @Test
    void getBookById() {
        System.out.println(bookController.getBookById(UUID.randomUUID()));
    }
}