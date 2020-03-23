package com.wen.crawler.Tools.PipeLine;

import com.wen.crawler.model.Book;
import com.wen.crawler.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;


@Component
public class InsertBookPipeLine implements Pipeline {
    @Autowired
    private BookService bookService;

    @Override
    public void process(ResultItems resultItems, Task task) {
        //通过resultItems获取爬取的信息
        List<Book> bookList = resultItems.get("bookList");
        for(Book book:bookList){
            if(!bookService.getBookIdByAuthorAndName(book.getAuthor(),book.getName()))
                bookService.insertOne(book);
        }
    }
}
