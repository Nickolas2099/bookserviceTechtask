package com.ifortex.bookservice.controller;

import com.ifortex.bookservice.dto.SearchCriteria;
import com.ifortex.bookservice.model.Book;
import com.ifortex.bookservice.service.BookService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("api/v1/books")
public class BookController {

  private final BookService bookService;

  public BookController(@Qualifier(value = "bookServiceImpl") BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping("statistic")
  public Map<String, Long> getStatistic() {
    return bookService.getBooks();
  }

  @GetMapping("search")
  public List<Book> findBooks(@RequestBody @Nullable SearchCriteria searchCriteria) {
    return bookService.getAllByCriteria(searchCriteria);
  }
}
