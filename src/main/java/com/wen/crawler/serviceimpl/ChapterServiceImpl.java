package com.wen.crawler.serviceimpl;

import com.wen.crawler.dao.ChapterDao;
import com.wen.crawler.model.Chapter;
import com.wen.crawler.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ChapterDao chapterDao;

    @Override
    public List<Chapter> getAll() {
        return chapterDao.findAll();
    }

    @Override
    public long insertOne(Chapter chapter) {
        return chapterDao.saveAndFlush(chapter).getId();
    }

    @Override
    public List<Chapter> getByBookId(long bookId) {
        return chapterDao.getByBookId(bookId);
    }


    @Override
    public void updateOne(Chapter chapter) {
        chapterDao.saveAndFlush(chapter);
    }

    @Override
    public void deleteOne(Chapter entity) {
        chapterDao.delete(entity);
    }

    @Override
    public void deleteById(Long aLong) {
        chapterDao.deleteById(aLong);
    }

    @Override
    public Chapter getById(Long aLong) {
        return chapterDao.getOne(aLong);
    }

    @Override
    public Chapter getOneBookOneChapter(long bookId, long chapterId) {
        return chapterDao.getOneBookOneChapter(bookId,chapterId);
    }
}
