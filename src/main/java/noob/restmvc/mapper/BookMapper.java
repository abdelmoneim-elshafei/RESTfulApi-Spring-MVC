package noob.restmvc.mapper;

import noob.restmvc.entity.Book;
import noob.restmvc.model.BookDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BookMapper {
    Book bookDTOTobook(BookDTO bookDTO);
    BookDTO bookToBookDTO(Book book);
}
