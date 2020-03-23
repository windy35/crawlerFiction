package com.wen.crawler.Tools.PageProcessor;

import com.wen.crawler.Tools.RegexMatchStr;
import com.wen.crawler.Tools.StringDateInterconversion;
import com.wen.crawler.model.Book;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;


public class UpdateBookDetailInfoProcessor implements PageProcessor {


    private Book book;

    public UpdateBookDetailInfoProcessor(Book book) {
        this.book = book;
    }


    // 抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    //爬虫配置信息设置
    private Site site = new Site()
            .setCharset("utf-8")    //设置编码
            .setSleepTime(500)        //设置抓取间隔
            .setTimeOut(Integer.MAX_VALUE)           //设置超时时间
            .setRetrySleepTime(1000)    //设置重试时间
            .setRetryTimes(Integer.MAX_VALUE);       //设置重试次数
    private static int count =0;

    @Override
    public Site getSite() {
        return site;
    }
    @Override
    public void process(Page page) {
        //获取最新章节名称
        book.setLastChapter(RegexMatchStr.getTargetStr(">(.*?)<",page.getHtml().xpath("//*[@id=\"info\"]/p[4]/a").get()).replace(">","").replace("<",""));
        //获取最新章节链接
        book.setLastChapterHref(RegexMatchStr.getTargetStr("a href=\"(.*?)\"",page.getHtml().xpath("//*[@id=\"info\"]/p[4]/a").toString()).replace("a href=\"","").replace("\"",""));
        //获取更新时间信息
        book.setLastUpdateTime(StringDateInterconversion.strToDateLong(page.getHtml().xpath("//*[@id=\"info\"]/p[3]").toString().replace("<p>最后更新：","").replace("</p>","")));
        //获取书本简介
        book.setIntroduction(page.getHtml().xpath("//*[@id=\"intro\"]/p[2]").get().replace("<p> ","").replace("</p>",""));
        //获取书本封面图片本地路径
        book.setLocalCoverImgPath("/static/images/cover/"+book.getBookId()+".jpg");
        //获取书本封面图片网络路径
        book.setWebCoverImgPath(page.getHtml().xpath("//*[@id=\"fmimg\"]/img/@src").get());
        page.putField("book",book);
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
