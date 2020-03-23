package com.wen.crawler.dao;

import com.wen.crawler.model.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ChapterDao extends JpaRepository<Chapter, Long>{

    @Query(value = "SELECT * FROM chapter where book_id=?1", nativeQuery = true)
    List<Chapter> getByBookId(long bookId);

    @Query(value = "SELECT * FROM chapter where book_id=?1 AND chapter_id=?2", nativeQuery = true)
    Chapter getOneBookOneChapter(long bookId, long chapterId);
}
