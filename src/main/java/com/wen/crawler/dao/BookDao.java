package com.wen.crawler.dao;

import com.wen.crawler.model.Book;
import com.wen.crawler.vo.BookChapterVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookDao extends JpaRepository<Book, Long>{

    @Query(value = "SELECT * FROM book WHERE author = ?1 AND NAME = ?2", nativeQuery = true)
    Book getBookIdByAuthorAndName(String author,String name);

    @Query(value = "SELECT * FROM book WHERE NAME = ?1 AND href = ?2", nativeQuery = true)
    Book getBookId(String name,String href);


    @Query(value = "SELECT * FROM book WHERE type like %?1%", nativeQuery = true)
    List<Book> getByType(String type);

    @Query(value = "SELECT * FROM book WHERE type like %?1% ORDER BY heat DESC LIMIT ?2,?3", nativeQuery = true)
    List<Book> getByTypeLimit(String type,int offset,int limit);


    @Query(value = "SELECT * FROM book ORDER BY heat DESC LIMIT ?1,?2", nativeQuery = true)
    List<Book> getAllBooksLimit(int offset,int limit);

    @Query(value = "SELECT * FROM book ORDER BY heat DESC", nativeQuery = true)
    List<Book> getAllBooks();

    @Query(value = "SELECT COUNT(book_id = ? OR NULL) FROM chapter", nativeQuery = true)
    long getOneBookChapterCount(long bookId);

//    @Query(value = "SELECT " +
//            "b.book_id," +
//            "b.introduction," +
//            "b.local_cover_img_path," +
//            "b.web_cover_img_path," +
//            "b.author," +
//            "b.heat," +
//            "b.href," +
//            "b.last_chapter," +
//            "b.last_chapter_href," +
//            "b.last_update_time," +
//            "b.name," +
//            "b.type," +
//            "c.content," +
//            "c.href chapterHref " +
//            "FROM book b LEFT JOIN chapter c ON b.`book_id` = ?1", nativeQuery = true)
//    List<BookChapterVo> getOneBookDetailInfo(long bookId);
        @Query(value = "SELECT b.*,c.`title`,c.`href` chapterHref,c.`content` FROM book b LEFT JOIN chapter c ON b.`book_id` = ?1", nativeQuery = true)
        BookChapterVo getOneBookDetailInfo(long bookId);
}
