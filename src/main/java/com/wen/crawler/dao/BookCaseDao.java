package com.wen.crawler.dao;

import com.wen.crawler.model.BookCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookCaseDao extends JpaRepository<BookCase, Long> {
    @Query(value = "SELECT * FROM book_case WHERE users_id = ?1 AND book_id = ?2", nativeQuery = true)
    BookCase isExistBook(long usersId, long bookId);

    @Query(value = "SELECT * FROM book_case WHERE users_id = ?1 ", nativeQuery = true)
    List<BookCase> getByUsersId(long usersId);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE book_case SET tag_chapter_name = ?3,tag_chapter_id = ?4,tag_last_update_time = ?5 WHERE users_id = ?1 And book_id = ?2", nativeQuery = true)
    void updateOne(long usersId,long bookId,String tagName,long tagId,String time);

    @Query(value = "SELECT * FROM book_case WHERE book_id = ?1 ", nativeQuery = true)
    List<BookCase> getByBookId(long parseLong);
}
