package noob.restmvc.services;

import lombok.extern.slf4j.Slf4j;
import noob.restmvc.model.BookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BookServiceImpl implements BookService {
    Map<UUID, BookDTO> booksMap;

    public BookServiceImpl() {
        booksMap = new HashMap<>();
        BookDTO bookDTO1 = BookDTO.builder()
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
        BookDTO bookDTO2 = BookDTO.builder()
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

        BookDTO bookDTO3 = BookDTO.builder()
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

        booksMap.put(bookDTO1.getId(), bookDTO1);
        booksMap.put(bookDTO2.getId(), bookDTO2);
        booksMap.put(bookDTO3.getId(), bookDTO3);
    }

    @Override
    public Page<BookDTO> getAllBooks(String title, String isbn, Integer pageNumber, Integer PageSize){
        return new PageImpl<>(new ArrayList<>(booksMap.values()));
    }
    @Override
    public Optional<BookDTO> getBookById(UUID id) {
        log.debug("Hello From Service" + id);
        return Optional.of(booksMap.get(id));
    }

    @Override
    public BookDTO creeateNewBook(BookDTO bookDTO) {
        BookDTO newBookDTO = BookDTO.builder()
                .id(UUID.randomUUID())
                .title(bookDTO.getTitle())
                .isbn(bookDTO.getIsbn())
                .quantityOnHand(bookDTO.getQuantityOnHand())
                .author(bookDTO.getAuthor())
                .price(bookDTO.getPrice())
                .createdData(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .version(1)
                .build();
        booksMap.put(newBookDTO.getId(), newBookDTO);
        return booksMap.get(newBookDTO.getId());
    }

    @Override
    public void updateBookById(UUID id, BookDTO bookDTO) {
        BookDTO existBookDTO = booksMap.get(id);
        existBookDTO.setAuthor(bookDTO.getAuthor());
        existBookDTO.setTitle(bookDTO.getTitle());
        existBookDTO.setPrice(bookDTO.getPrice());
        existBookDTO.setQuantityOnHand(bookDTO.getQuantityOnHand());
        existBookDTO.setIsbn(bookDTO.getIsbn());

        booksMap.put(existBookDTO.getId(), existBookDTO);
    }

    @Override
    public void deleteBookById(UUID id) {
       booksMap.remove(id);
    }

    @Override
    public void updateBookByIdByPatchMethod(UUID id, BookDTO bookDTO) {
        BookDTO existBookDTO = booksMap.get(id);

        if(StringUtils.hasText(existBookDTO.getTitle())){
           existBookDTO.setTitle(bookDTO.getTitle());
        }
        if(StringUtils.hasText(existBookDTO.getIsbn())){
            existBookDTO.setIsbn(bookDTO.getIsbn());
        }
        if(existBookDTO.getPrice() != null){
            existBookDTO.setPrice(bookDTO.getPrice());
        }
        if(existBookDTO.getQuantityOnHand() != null){
            existBookDTO.setQuantityOnHand(bookDTO.getQuantityOnHand());
        }
        if(StringUtils.hasText(existBookDTO.getAuthor())){
            existBookDTO.setAuthor(bookDTO.getAuthor());
        }

        booksMap.put(existBookDTO.getId(), existBookDTO);
    }
}
