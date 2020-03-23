package com.wen.crawler.Tools.PageProcessor;

import com.wen.crawler.Tools.RegexMatchStr;
import com.wen.crawler.model.Book;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;


public class InsertAllBookPageNameAndHrefProcessor implements PageProcessor {

    private String xpath;

    public InsertAllBookPageNameAndHrefProcessor(String xpath) {
        this.xpath = xpath;
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
        double startTime, endTime;
        startTime = System.currentTimeMillis();
        //爬取的a标签结果存储于arr数组
        List<Book> bookList = new ArrayList<Book>();
        String[] arr = page.getHtml().xpath(this.getXpath()).all().toString().split(",");
        for(int i=0;i<arr.length;i++){
            //编译生成正则表达式并匹配
            Book book = new Book();
            book.setName(RegexMatchStr.getTargetStr("[\\u4E00-\\u9FA5](.*?)",arr[i]));
            //获取去除双引号后的字符串
            String bookHref = RegexMatchStr.getTargetStr("\"(.*?)\"",arr[i]).replace("\"","");
            book.setHref(bookHref);
            bookList.add(book);
            count++;
            page.putField("bookList",bookList);
        }
        endTime = System.currentTimeMillis();
        System.out.println("爬取结束，耗时约" + ((endTime - startTime) / 1000) + "秒，抓取了"+count+"条记录");
    }

    public String getXpath() {
        return xpath;
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
    }
}
