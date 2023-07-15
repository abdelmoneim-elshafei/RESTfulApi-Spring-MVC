package noob.restmvc.services;

import noob.restmvc.model.BookCSVRecord;

import java.io.File;
import java.util.List;

public interface BookCSVService {
    List<BookCSVRecord> convertCSV(File csvFile);
}
