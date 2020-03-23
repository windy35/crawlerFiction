package com.wen.crawler.service;

import com.wen.crawler.model.Book;
import com.wen.crawler.model.Chapter;
import com.wen.crawler.vo.BookChapterVo;

import java.util.List;
import java.util.Optional;

public interface BookService extends BaseService<Book,Long>{

    boolean getBookIdByAuthorAndName(String author,String name);

    long getBookId(String name,String href);

    void updateAll(List<Book> bookList);

    List<Book> getByType(String type);

    List<Book> getByTypeLimit(String type,int offset,int limit);

    List<Book> getAllBooks();

    List<Book> getAllBooksLimit(int offset,int limit);

    BookChapterVo getOneBookDetailInfo(long bookId);

    Optional<Book> getOneBookBasicInfo(long bookId);

    long getOneBookChapterCount(long bookId);
}
