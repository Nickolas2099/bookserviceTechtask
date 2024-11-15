package com.ifortex.bookservice.repository;

import com.ifortex.bookservice.model.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Member findMember() {
        String sql =
                "WITH romance_book AS ( " +
                "   SELECT id, publication_date " +
                "   FROM books " +
                "   WHERE 'Romance' = ANY(genre) " +
                "   ORDER BY publication_date " +
                "   LIMIT 1) " +
                "SELECT m.* " +
                "FROM members m " +
                "   JOIN member_books mb ON m.id = mb.member_id " +
                "   JOIN romance_book b ON mb.book_id = b.id " +
                "ORDER BY m.membership_date DESC " +
                "LIMIT 1";
        return (Member) entityManager.createNativeQuery(sql, Member.class).getSingleResult();
    }


    public List<Member> findMembers() {
        String sql =
                "SELECT * " +
                "FROM members " +
                        "LEFT JOIN member_books mb ON members.id = mb.member_id " +
                        "WHERE DATE_PART('year', membership_date) = 2023 " +
                        "AND mb.member_id is NULL";
        return entityManager.createNativeQuery(sql, Member.class).getResultList();
    }
}
