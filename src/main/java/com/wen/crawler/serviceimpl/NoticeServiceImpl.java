package com.wen.crawler.serviceimpl;

import com.wen.crawler.dao.NoticeDao;
import com.wen.crawler.model.Notice;
import com.wen.crawler.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    NoticeDao noticeDao;

    @Override
    public long insertOne(Notice notice) {
        return noticeDao.save(notice).getNoticeId();
    }

    @Override
    public void deleteOne(Notice notice) {
        noticeDao.delete(notice);
    }

    @Override
    public List<Notice> getAll() {
        return noticeDao.findAll();
    }

    @Override
    public void updateOne(Notice notice) {
        noticeDao.saveAndFlush(notice);
    }

    @Override
    public void deleteById(Long aLong) {
        noticeDao.deleteById(aLong);
    }

    @Override
    public Notice getById(Long aLong) {
        return noticeDao.findById(aLong).get();
    }
}
