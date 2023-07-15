package noob.restmvc.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCSVRecord {

    @CsvBindByName(column = "original_title")
    String title;

    @CsvBindByName(column = "authors")
    String author;

    @CsvBindByName(column = "isbn")
    String isbn;
}
