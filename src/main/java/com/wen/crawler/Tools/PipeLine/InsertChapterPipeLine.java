package com.wen.crawler.Tools.PipeLine;

import com.wen.crawler.model.Book;
import com.wen.crawler.model.Chapter;
import com.wen.crawler.service.BookService;
import com.wen.crawler.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.ArrayList;
import java.util.List;

@Component
public class InsertChapterPipeLine implements Pipeline {
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private BookService bookService;
    @Override
    public void process(ResultItems resultItems, Task task) {
        //通过resultItems获取爬取的信息
        List<Chapter> chapterList = resultItems.get("chapterList");
        Book book = resultItems.get("book");
        if(!bookService.getBookIdByAuthorAndName(book.getAuthor(),book.getName()))
            bookService.updateOne(book);
        List<Chapter> newChapterList = new ArrayList<Chapter>();
        for(Chapter chapter:chapterList)
            if(chapterService.getOneBookOneChapter(book.getBookId(),chapter.getChapterId())==null)
            {
                chapterService.insertOne(chapter);
                newChapterList.add(chapter);
            }
        for(int i = 0;i<newChapterList.size();i++){
            Chapter chapter = chapterList.get(i);
            Chapter preChapter = chapterService.getOneBookOneChapter(book.getBookId(),(chapter.getChapterId()-1));
            if(chapter.getChapterId() == 1){
                Chapter nextChapter = chapterService.getOneBookOneChapter(book.getBookId(),(chapter.getChapterId()+1));
                if(nextChapter!=null)
                    chapter.setNextChapterId(nextChapter.getId());
            }
            if(preChapter != null){
                preChapter.setNextChapterId(chapter.getId());
                chapter.setPreChapterId(preChapter.getId());
                chapterService.updateOne(preChapter);
            }
            chapterService.updateOne(chapter);
        }
    }
}
