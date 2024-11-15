package com.ifortex.bookservice.repository;

import com.ifortex.bookservice.model.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Object[]> getBooks() {
        String sql = "SELECT genre, COUNT(*) AS book_count " +
                "FROM (" +
                "   SELECT unnest(genre) AS genre " +
                "   FROM books" +
                ") AS genre_array " +
                "GROUP BY genre " +
                "ORDER BY book_count DESC";
        return entityManager.createNativeQuery(sql).getResultList();
    }

    public List<Book> getAllByCriteria(String title, String author,
                                         String genre, String description, Integer year) {
        StringBuilder sql = new StringBuilder("SELECT * FROM books WHERE true");

        if(title != null && !title.isEmpty()) {
            sql.append(" AND title ILIKE CONCAT('%', :title, '%') ");
        }
        if(author != null && !author.isEmpty()) {
            sql.append(" AND author ILIKE CONCAT('%', :author, '%')");
        }
        if(genre != null && !genre.isEmpty()) {
            sql.append("AND EXISTS(" +
                    " SELECT 1" +
                    " FROM unnest(genre) AS genre_arr" +
                    " WHERE genre_arr ILIKE CONCAT('%', :genre, '%'))");
        }
        if(description != null && !description.isEmpty()) {
            sql.append(" AND description ILIKE CONCAT('%', :description, '%')");
        }
        if(year != null) {
            sql.append(" AND DATE_PART('year', publication_date) = :year");
        }

        sql.append(" ORDER BY publication_date DESC");

        Query query = entityManager.createNativeQuery(sql.toString(), Book.class);

        if(title != null && !title.isEmpty()) {
            query.setParameter("title", title);
        }
        if(author != null && !author.isEmpty()) {
            query.setParameter("author", author);
        }
        if(genre != null && !genre.isEmpty()) {
            query.setParameter("genre", genre);
        }
        if(description != null && !description.isEmpty()) {
            query.setParameter("description", description);
        }
        if(year != null) {
            query.setParameter("year", year);
        }

        return query.getResultList();
    }


}
