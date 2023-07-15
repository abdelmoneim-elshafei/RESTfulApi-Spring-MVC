package noob.restmvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import noob.restmvc.entity.Book;
import noob.restmvc.exception.NotFoundException;
import noob.restmvc.mapper.BookMapper;
import noob.restmvc.model.BookDTO;
import noob.restmvc.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import static org.hamcrest.core.Is.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BookControllerIT {
    @Autowired
    BookController controller;

    @Autowired
    BookRepository repository;

    @Autowired
    BookMapper mapper;

    @Autowired
    WebApplicationContext wac;

    @Autowired
    ObjectMapper objectMapper;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
       mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    @Test
    void getBookByIsbnQueryParam() throws Exception {
        mockMvc.perform(get(BookController.BOOK_PATH)
                        .queryParam("isbn","097915930X")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()",is(1)));
    }

    @Test
    void getBookByTitleQueryParam() throws Exception {
        mockMvc.perform(get(BookController.BOOK_PATH)
                .queryParam("title","Chasm City")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()",is(1)));
    }

    @Test
    void updateBookByIdByPatchMethod() throws Exception {
        Book book = repository.findAll().get(0);
        Map<String, String> bookMap = new HashMap<>();
        bookMap.put("title", "NewTitle 111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
       MvcResult mvcResult= mockMvc.perform(patch(BookController.BOOK_PATH_ID, book.getId())
                        .content(objectMapper.writeValueAsBytes(bookMap))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.length()",is(1)))
               .andExpect(status().isBadRequest()).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }
    @Test
    void deleteByIdNotFound() {
        assertThrows(NotFoundException.class, ()->{
            controller.deleteBookById(UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void deleteById(){
        Book book = repository.findAll().get(0);
        ResponseEntity<?> re = controller.deleteBookById(book.getId());
        assertThat(re.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        assertThat(repository.findById(book.getId())).isEmpty();

    }

    @Test
    void updateByIdNotFound(){
        assertThrows(NotFoundException.class, () ->{
          controller.updateBookById(UUID.randomUUID(),BookDTO.builder().build());
        });
    }

    @Test
    void updateById() {
        Book book = repository.findAll().get(0);
        BookDTO bookDTO = mapper.bookToBookDTO(book);
        bookDTO.setId(null);
        bookDTO.setVersion(null);
        String newName = "New Title";
        bookDTO.setTitle(newName);
        ResponseEntity<?> re = controller.updateBookById(book.getId(),bookDTO);
        assertThat(re.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Book update = repository.findById(book.getId()).get();
        assertThat(update.getTitle()).isEqualTo(newName);
    }

    @Rollback
    @Transactional
    @Test
    void createNewBook(){
        BookDTO bookDTO = BookDTO.builder()
                .title("New Book")
                .build();

        ResponseEntity<?> responseEntity = controller.createNewBook(bookDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();
        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID uuid = UUID.fromString(locationUUID[4]);
        assertThat(repository.findById(uuid).get().getTitle()).isEqualTo(bookDTO.getTitle());
    }

    @Test
    void getBookByIdNotFound(){
        assertThrows(NotFoundException.class, ()->{
            controller.getBookById(UUID.randomUUID());
        });
    }

    @Test
    void getBookById(){
        Book book = repository.findAll().get(0);
        BookDTO bookDTO = controller.getBookById(book.getId());
        assertThat(book.getTitle()).isEqualTo(bookDTO.getTitle());
    }

    @Test
    void testGetAllBooks(){
        Page<BookDTO> list  = controller.getAllBooks(null, null,0,25);
        assertThat(list.getContent().size()).isEqualTo(25);
    }

    @Rollback
    @Transactional
    @Test
    void testGetAllBooksEmpty(){
        repository.deleteAll();
        Page<BookDTO> list  = controller.getAllBooks(null,null , 0, 25);
        assertThat(list.getContent().size()).isEqualTo(0);
    }


}