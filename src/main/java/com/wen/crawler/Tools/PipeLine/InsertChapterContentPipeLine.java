package com.wen.crawler.Tools.PipeLine;

import com.wen.crawler.model.Chapter;
import com.wen.crawler.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;


@Component
public class InsertChapterContentPipeLine implements Pipeline {
    @Autowired
    private ChapterService chapterService;

    @Override
    public void process(ResultItems resultItems, Task task) {
        //通过resultItems获取爬取的信息
        Chapter chapterContent = resultItems.get("chapterContent");
        chapterService.insertOne(chapterContent);
    }
}
