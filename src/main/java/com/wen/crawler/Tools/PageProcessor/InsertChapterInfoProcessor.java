package com.wen.crawler.Tools.PageProcessor;

import com.wen.crawler.Tools.RegexMatchStr;
import com.wen.crawler.Tools.StringDateInterconversion;
import com.wen.crawler.model.Book;
import com.wen.crawler.model.Chapter;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;


public class InsertChapterInfoProcessor implements PageProcessor {

    private Book book;

    public InsertChapterInfoProcessor(Book book) {
        this.book = book;
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
        List<Chapter> chapterList = new ArrayList<Chapter>();
        String[] arr = page.getHtml().xpath("//*[@id=\"list\"]/dl/dd/a").all().toString().split(",");
        String intro = page.getHtml().xpath("//*[@id=\"intro\"]/p[2]/text()").get();
        String webImgPath = page.getHtml().xpath("//*[@id=\"fmimg\"]/img/@src").get();
        String ludt = page.getHtml().xpath("//*[@id=\"info\"]/p[3]/text()").get();
        for(int i=0;i<arr.length;i++){
            Chapter chapter = new Chapter();
            //编译生成正则表达式并匹配
            chapter.setTitle(RegexMatchStr.getTargetStr(">(.*)?<",arr[i]).replace(">","").replace("<",""));
            chapter.setHref("http://www.xbiquge.la"+ RegexMatchStr.getTargetStr("\"(.*?)\"",arr[i]).replace("\"",""));
            chapter.setBookId(book.getBookId());
            chapterList.add(chapter);
            //设置书本剩余信息
            book.setWebCoverImgPath(webImgPath);
            book.setLastUpdateTime(StringDateInterconversion.strToDateLong(ludt.replace("最后更新：","")));
            book.setIntroduction(intro);
            book.setLocalCoverImgPath("/static/img/cover/"+book.getAuthor()+"_"+book.getName()+".jpg");
        }
         page.putField("chapterList",chapterList);
         page.putField("book",book);
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
