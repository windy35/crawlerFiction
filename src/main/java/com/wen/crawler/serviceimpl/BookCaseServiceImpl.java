package com.wen.crawler.serviceimpl;

import com.wen.crawler.dao.BookCaseDao;
import com.wen.crawler.model.BookCase;
import com.wen.crawler.service.BookCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookCaseServiceImpl implements BookCaseService {
    @Autowired
    BookCaseDao bookCaseDao;

    @Override
    public long insertOne(BookCase bookCase) {
        return bookCaseDao.save(bookCase).getId();
    }

    @Override
    public void deleteOne(BookCase bookCase) {
        bookCaseDao.delete(bookCase);
    }

    @Override
    public List<BookCase> getAll() {
        return bookCaseDao.findAll();
    }

    @Override
    public void updateOne(BookCase bookCase) {
        bookCaseDao.saveAndFlush(bookCase);
    }

    @Override
    public boolean isExistBook(long usersId, long bookId) {
        BookCase bookCase = bookCaseDao.isExistBook(usersId,bookId);
        if(bookCase!=null)
            return true;
        return false;
    }

    @Override
    public List<BookCase> getByUsersId(long usersId) {
        return bookCaseDao.getByUsersId(usersId);
    }

    @Override
    public BookCase getById(Long aLong) {
        return bookCaseDao.getOne(aLong);
    }

    @Override
    public void deleteById(Long aLong) {
        bookCaseDao.deleteById(aLong);
    }

    @Override
    public void updateOne(long usersId, long bookId, String tagName, long tagId, String time) {
        bookCaseDao.updateOne(usersId,bookId,tagName,tagId,time);
    }

    @Override
    public List<BookCase> getByBookId(long parseLong) {
        return bookCaseDao.getByBookId(parseLong);
    }
}
