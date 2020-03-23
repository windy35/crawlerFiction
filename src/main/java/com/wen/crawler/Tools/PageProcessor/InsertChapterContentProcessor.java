package com.wen.crawler.Tools.PageProcessor;

import com.wen.crawler.model.Chapter;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class InsertChapterContentProcessor implements PageProcessor {

    private Chapter chapter;

    public InsertChapterContentProcessor(Chapter chapter) {
        this.chapter = chapter;
    }

    // 抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    //爬虫配置信息设置
    private Site site = new Site()
            .setCharset("utf-8")    //设置编码
            .setSleepTime(500)        //设置抓取间隔
            .setTimeOut(Integer.MAX_VALUE)       //设置超时时间
            .setRetrySleepTime(1000)    //设置重试时间
            .setRetryTimes(Integer.MAX_VALUE);       //设置重试次数
    private static int count =0;

    @Override
    public Site getSite() {
        return site;
    }
    @Override
    public void process(Page page) {
        String content = page.getHtml().xpath("//*[@id=\"content\"]/text()").get();
        //编译生成正则表达式并匹配
        this.getChapter().setContent(content);
         page.putField("chapterContent",this.getChapter());
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }
}
