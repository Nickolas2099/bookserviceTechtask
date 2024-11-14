package com.ifortex.bookservice.service.impl;

import com.ifortex.bookservice.dto.SearchCriteria;
import com.ifortex.bookservice.model.Book;
import com.ifortex.bookservice.repository.BookRepository;
import com.ifortex.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Map<String, Long> getBooks() {

        List<Object[]> bookCounts = bookRepository.getBooks();
        return bookCounts.stream()
                    .collect(Collectors.toMap(
                            bookCount -> (String) bookCount[0],
                            bookCount -> (Long) bookCount[1],
                            (v1, v2) -> v1,
                            LinkedHashMap::new
                    ));
    }

    @Override
    public List<Book> getAllByCriteria(SearchCriteria criteria) {
        if(criteria == null) {
            log.info("SearchCriteria is NULL");
            criteria = SearchCriteria.builder().build();
        }
        return bookRepository.getAllByCriteria(criteria.getTitle(), criteria.getAuthor(),
                criteria.getGenre(), criteria.getDescription(), criteria.getYear());
    }
}
