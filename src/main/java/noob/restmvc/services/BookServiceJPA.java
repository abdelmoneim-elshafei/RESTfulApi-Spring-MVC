package noob.restmvc.services;

import lombok.RequiredArgsConstructor;
import noob.restmvc.entity.Book;
import noob.restmvc.exception.NotFoundException;
import noob.restmvc.mapper.BookMapper;
import noob.restmvc.model.BookDTO;
import noob.restmvc.repository.BookRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BookServiceJPA implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper mapper;
    private final  Integer PAGE_SIZE = 25;
    private final  Integer PAGE_NUMBER= 0;

    @Override
    public Page<BookDTO> getAllBooks(String title, String isbn, Integer pageNumber, Integer PageSize) {
        PageRequest pageRequest = createPageRequest(pageNumber,PageSize);
        Page<Book> books;
        if(StringUtils.hasText(title)){
            books = listBookByTitle(title,pageRequest);
        }else if(StringUtils.hasText(isbn)){
           books = listBookByIsbn(isbn,pageRequest);
        }else {
            books = bookRepository.findAll(pageRequest);
        }
        return books.map(mapper::bookToBookDTO);
    }

    PageRequest createPageRequest(Integer pageNumber, Integer PageSize){
        int queryPageNo;
        int queryPageSi;
        if(pageNumber != null && pageNumber > 0){
          queryPageNo = pageNumber;
        }else {
            queryPageNo = PAGE_NUMBER;
        }
        if(PageSize == null){
            queryPageSi = PAGE_SIZE;
        }else {
            if(PageSize > 500)
                queryPageSi = 500;
            else
                queryPageSi =PageSize;
        }
        Sort sort = Sort.by(Sort.Order.asc("title"));
        return PageRequest.of(queryPageNo,queryPageSi,sort);

    }

    Page<Book> listBookByTitle(String title,PageRequest p){
        return bookRepository.findBookByTitleLikeIgnoreCase(title, p);
    }
    Page<Book> listBookByIsbn(String isbn, PageRequest p){
        return bookRepository.findBookByIsbn(isbn, p);
    }

    @Override
    public Optional<BookDTO> getBookById(UUID id) {
        return Optional.ofNullable(mapper.bookToBookDTO(bookRepository.findById(id).orElseThrow(
                NotFoundException::new)));
    }

    @Override
    public BookDTO creeateNewBook(BookDTO bookDTO) {
        if (bookDTO == null)
            throw new NotFoundException();
        return mapper.bookToBookDTO(bookRepository
                .save(mapper.bookDTOTobook(bookDTO)));
    }

    @Override
    public void updateBookById(UUID id, BookDTO bookDTO) {
       Optional<Book> exist = bookRepository.findById(id);
       if(exist.isPresent()){
           exist.get().setAuthor(bookDTO.getAuthor());
           exist.get().setTitle(bookDTO.getTitle());
           exist.get().setIsbn(bookDTO.getIsbn());
           exist.get().setQuantityOnHand(bookDTO.getQuantityOnHand());
           exist.get().setPrice(bookDTO.getPrice());
           bookRepository.save(exist.get());

       }else
           throw new NotFoundException("Can't Update Not Found");
    }

    @Override
    public void deleteBookById(UUID id) {
        if(bookRepository.findById(id).isPresent())
            bookRepository.deleteById(id);
        else
            throw new NotFoundException("Can't Delete Not found");
    }

    @Override
    public void updateBookByIdByPatchMethod(UUID id, BookDTO bookDTO) {
        Optional<Book> exist = bookRepository.findById(id);
        if(exist.isPresent()){
            if(StringUtils.hasText(bookDTO.getAuthor()))
                exist.get().setAuthor(bookDTO.getAuthor());
            if(StringUtils.hasText(bookDTO.getTitle()))
                exist.get().setTitle(bookDTO.getTitle());
            if(StringUtils.hasText(bookDTO.getIsbn()))
                exist.get().setIsbn(bookDTO.getIsbn());
            if(bookDTO.getQuantityOnHand() != null)
                exist.get().setQuantityOnHand(bookDTO.getQuantityOnHand());
            if(bookDTO.getPrice() != null)
                exist.get().setPrice(bookDTO.getPrice());


            bookRepository.save(exist.get());

        }else
            throw new NotFoundException("Can't update By Patch Not Found");
    }
}
