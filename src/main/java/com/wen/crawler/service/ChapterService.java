package com.wen.crawler.service;

import com.wen.crawler.model.Chapter;

import java.util.List;
import java.util.Optional;

public interface ChapterService extends BaseService<Chapter,Long>{

    List<Chapter> getByBookId(long bookId);

    Chapter getOneBookOneChapter(long bookId, long chapterId);
}
