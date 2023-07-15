package noob.restmvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import noob.restmvc.exception.NotFoundException;
import noob.restmvc.model.BookDTO;
import noob.restmvc.services.BookService;
import noob.restmvc.services.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest
class BookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BookService bookService;

    @Captor
    ArgumentCaptor<UUID> captor;
    @Captor
    ArgumentCaptor<BookDTO> bookCaptor;


    BookServiceImpl impl;

    @BeforeEach
    void setUp() {
        impl = new BookServiceImpl();
    }

    @Test
    void createNewBookNullName() throws Exception {
        BookDTO bookDTO = BookDTO.builder().build();
        given(bookService.creeateNewBook(any(BookDTO.class)))
                .willReturn(impl.getAllBooks(null, null, 0, 25).getContent().get(0));
        MvcResult mvcResult = mockMvc.perform(post(BookController.BOOK_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(bookDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400)).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void getByIdNotFound() throws Exception {
        given(bookService.getBookById(any(UUID.class))).willThrow(NotFoundException.class);
        mockMvc.perform(get(BookController.BOOK_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateBookByIdByPatchMethod() throws Exception {
        BookDTO bookDTO = impl.getAllBooks(null, null, 0, 25).getContent().get(0);
        Map<String, String> bookMap = new HashMap<>();
        bookMap.put("title", "NewTitle");
        mockMvc.perform(patch(BookController.BOOK_PATH_ID, bookDTO.getId())
                        .content(objectMapper.writeValueAsBytes(bookMap))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(bookService).updateBookByIdByPatchMethod(captor.capture(), bookCaptor.capture());
        assertThat(bookDTO.getId()).isEqualTo(captor.getValue());
        assertThat(bookMap.get("title")).isEqualTo(bookCaptor.getValue().getTitle());
    }

    @Test
    void getBookById() throws Exception {
        BookDTO bookDTO = impl.getAllBooks(null, null, 0, 25).getContent().get(0);
        given(bookService.getBookById(bookDTO.getId())).willReturn(Optional.of(bookDTO));

        mockMvc.perform(get(BookController.BOOK_PATH_ID, bookDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(bookDTO.getId().toString())))
                .andExpect(jsonPath("$.title", is(bookDTO.getTitle())));
        ;

        verify(bookService).getBookById(captor.capture());
        assertThat(bookDTO.getId()).isEqualTo(captor.getValue());

    }

    @Test
    void getAllBooks() throws Exception {
        Page<BookDTO> bookDTOS = impl.getAllBooks(null, null, 0, 25);
        when(bookService.getAllBooks(any(), any(), any(), any())).thenReturn(bookDTOS);
        mockMvc.perform(get(BookController.BOOK_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.length()", is(bookDTOS.getContent().size())));

    }

    @Test
    void createNewBook() throws Exception {
        BookDTO bookDTO = BookDTO.builder()
                .title("test")
                .author("abdo")
                .quantityOnHand(300)
                .price(new BigDecimal("34"))
                .isbn("348-38775757")
                .build();
        when(bookService.creeateNewBook(bookDTO)).thenReturn(bookDTO);
        mockMvc.perform(post(BookController.BOOK_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(bookDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.title", is(bookDTO.getTitle())));
        verify(bookService).creeateNewBook(bookCaptor.capture());
        assertThat(bookDTO.getAuthor()).isEqualTo(bookCaptor.getValue().getAuthor());
    }

    @Test
    void updateBookById() throws Exception {
        BookDTO bookDTO = impl.getAllBooks(null, null, 0, 25).getContent().get(0);
        mockMvc.perform(put(BookController.BOOK_PATH_ID, bookDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(bookDTO)))
                .andExpect(status().isNoContent());

        verify(bookService).updateBookById(captor.capture(), bookCaptor.capture());
        assertThat(bookDTO).isEqualTo(bookCaptor.getValue());
    }

    @Test
    void deleteBookById() throws Exception {

        BookDTO bookDTO = impl.getAllBooks(null, null, 0, 25).getContent().get(0);
        mockMvc.perform(delete(BookController.BOOK_PATH_ID, bookDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(bookService).deleteBookById(captor.capture());
        assertThat(bookDTO.getId()).isEqualTo(captor.getValue());

    }
}