package noob.restmvc.services;

import noob.restmvc.model.BookCSVRecord;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BookCSVServiceImplTest {

    BookCSVService bookCSVService = new BookCSVServiceImpl();
    @Test
    void convertCSV() throws FileNotFoundException {

        File file = ResourceUtils.getFile("classpath:csvdata/books.csv");
        List<BookCSVRecord> list = bookCSVService.convertCSV(file);

        System.out.println(list.size());

       assertThat(list.size()).isGreaterThan(0);
    }
}