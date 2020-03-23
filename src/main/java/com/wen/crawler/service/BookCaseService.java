package com.wen.crawler.service;

import com.wen.crawler.model.BookCase;

import java.util.List;

public interface BookCaseService extends BaseService<BookCase,Long>{
    boolean isExistBook(long usersId, long bookId);

    List<BookCase> getByUsersId(long usersId);

    void updateOne(long usersId,long bookId,String tagName,long tagId,String time);

    List<BookCase> getByBookId(long parseLong);
}
