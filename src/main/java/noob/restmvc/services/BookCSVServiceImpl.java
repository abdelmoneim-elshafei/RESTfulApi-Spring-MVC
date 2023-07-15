package noob.restmvc.services;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import noob.restmvc.model.BookCSVRecord;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Service
public class BookCSVServiceImpl implements BookCSVService {
    @Override
    public List<BookCSVRecord> convertCSV(File csvFile) {

        try {
            return new CsvToBeanBuilder<BookCSVRecord>(new FileReader(csvFile))
                    .withType(BookCSVRecord.class)
                    .build().parse();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
