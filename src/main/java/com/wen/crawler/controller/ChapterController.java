package com.wen.crawler.controller;

import com.wen.crawler.dao.ResultJSON;
import com.wen.crawler.model.Chapter;
import com.wen.crawler.service.BookService;
import com.wen.crawler.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;


    @RequestMapping(value = "/getOneChapterById/{chapterId}",method = RequestMethod.GET)
    public ResultJSON getOneChapterById(@PathVariable String chapterId){
        ResultJSON rs = new ResultJSON();
        Chapter chapterInfo = chapterService.getById(Long.parseLong(chapterId));
        rs.add("chapterInfo",chapterInfo);
        return rs;
    }
}
