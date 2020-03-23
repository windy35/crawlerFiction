package com.wen.crawler.serviceimpl;

import com.wen.crawler.dao.BookDao;
import com.wen.crawler.model.Book;
import com.wen.crawler.service.BookService;
import com.wen.crawler.vo.BookChapterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookDao bookDao;

    @Override
    public List<Book> getAll() {
        return bookDao.findAll();
    }

    @Override
    public long insertOne(Book book) {
        return bookDao.saveAndFlush(book).getBookId();
    }

    @Override
    public boolean getBookIdByAuthorAndName(String author,String name) {
        if(bookDao.getBookIdByAuthorAndName(author,name)!=null)
            return true;
        return false;
    }

    @Override
    public void deleteOne(Book book) {
        bookDao.delete(book);
    }

    @Override
    public long getBookId(String name,String href) {
        Book book = bookDao.getBookId(name,href);
        if(book == null)
            return -1;
        return book.getBookId();
    }

    @Override
    public void updateOne(Book book) {
        bookDao.saveAndFlush(book);
    }

    @Override
    public void updateAll(List<Book> bookList) {
        for(Book book:bookList)
            bookDao.saveAndFlush(book);
    }

    @Override
    public List<Book> getByType(String type) {
        return bookDao.getByType(type);
    }

    @Override
    public List<Book> getByTypeLimit(String type, int offset, int limit) {
        return bookDao.getByTypeLimit(type,offset,limit);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }

    @Override
    public List<Book> getAllBooksLimit(int offset, int limit) {
        return bookDao.getAllBooksLimit(offset,limit);
    }

    @Override
    public BookChapterVo getOneBookDetailInfo(long bookId) {
        return bookDao.getOneBookDetailInfo(bookId);
    }

    @Override
    public Optional<Book> getOneBookBasicInfo(long bookId) {
        return bookDao.findById(bookId);
    }

    @Override
    public long getOneBookChapterCount(long bookId) {
        return bookDao.getOneBookChapterCount(bookId);
    }

    @Override
    public void deleteById(Long aLong) {
        bookDao.deleteById(aLong);
    }

    @Override
    public Book getById(Long aLong) {
        return bookDao.getOne(aLong);
    }


}
