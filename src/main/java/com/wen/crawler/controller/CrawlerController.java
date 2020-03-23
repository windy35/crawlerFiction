package com.wen.crawler.controller;

import com.wen.crawler.Tools.Base64Util;
import com.wen.crawler.Tools.PageProcessor.InsertBookInfoProcessor;
import com.wen.crawler.Tools.PageProcessor.InsertChapterContentProcessor;
import com.wen.crawler.Tools.PageProcessor.InsertChapterInfoProcessor;
import com.wen.crawler.Tools.PageProcessor.UpdateBookDetailInfoProcessor;
import com.wen.crawler.Tools.PipeLine.InsertBookPipeLine;
import com.wen.crawler.Tools.PipeLine.InsertChapterContentPipeLine;
import com.wen.crawler.Tools.PipeLine.InsertChapterPipeLine;
import com.wen.crawler.Tools.PipeLine.UpdateBookPipeLine;
import com.wen.crawler.dao.ResultJSON;
import com.wen.crawler.model.Book;
import com.wen.crawler.model.Chapter;
import com.wen.crawler.service.BookService;
import com.wen.crawler.service.ChapterService;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;
import us.codecraft.webmagic.Spider;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/crawler")
public class CrawlerController {
    @Autowired
    private InsertBookPipeLine insertBookPipeLine;
    @Autowired
    private InsertChapterPipeLine insertChapterPipeLine;
    @Autowired
    private InsertChapterContentPipeLine insertChapterContentPipeLine;
    @Autowired
    private UpdateBookPipeLine updateBookPipeLine;
    @Autowired
    BookService bookService;
    @Autowired
    ChapterService chapterService;

    @PostMapping("/bookCrawler2")
    public ResultJSON updateBookInfo(){
        double startTime,endTime;
        startTime = System.currentTimeMillis();
        for(int i=1;i<=6;i++) {
//            for(int j=1;j<=InsertBookInfoProcessor.getLastPageNum();j++) {
            for(int j=1;j<=1;j++) {
                Spider.create(new InsertBookInfoProcessor(i))
                        .addUrl("http://www.xbiquge.la/fenlei/"+i+"_"+j+".html")
                        .thread(1)
                        .addPipeline(insertBookPipeLine)
                        //利用布隆过滤器设置网页去重
//                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(1000000)))
                        .run();
            }
        }
        endTime = System.currentTimeMillis();
        System.out.println("爬取结束，耗时约" + ((endTime - startTime) / 1000) + "秒");
        return ResultJSON.SUCCESS();
    }

    @PostMapping("/bookCrawler")
    public ResultJSON bookCrawler(){
        double startTime,endTime;
        startTime = System.currentTimeMillis();
        List<Book> bookList = bookService.getAllBooks();
        for(int i=0;i<bookList.size();i++) {
                Spider.create(new UpdateBookDetailInfoProcessor(bookList.get(i)))
                        .addUrl(bookList.get(i).getHref())
                        .thread(1)
                        .addPipeline(updateBookPipeLine)
                        //利用布隆过滤器设置网页去重
//                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(1000000)))
                        .run();
        }
        endTime = System.currentTimeMillis();
        System.out.println("爬取结束，耗时约" + ((endTime - startTime) / 1000) + "秒");
        return ResultJSON.SUCCESS();
    }

    @RequestMapping(value = "/chapterCrawler",method = RequestMethod.POST)
    public ResultJSON updateChapter(){
        double startTime,endTime;
        startTime = System.currentTimeMillis();
        List<Book> bookList = bookService.getAllBooks();
        for(int i=0;i<bookList.size();i++) {
            if (bookList.get(i).getHref() != null) {
                Spider.create(new InsertChapterInfoProcessor(bookList.get(i)))
                        .addUrl(bookList.get(i).getHref())
                        .thread(1)
                        .addPipeline(insertChapterPipeLine)
                        //利用布隆过滤器设置网页去重
//                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(1000000)))
                        .run();
            }
            System.out.println("完成第 " + (i+1) + " 本书的爬取操作");
        }
        endTime = System.currentTimeMillis();
        System.out.println("爬取结束，耗时约" + ((endTime - startTime) / 1000) + "秒");
        return ResultJSON.SUCCESS();
    }

    @RequestMapping(value = "/chapterContentCrawler",method = RequestMethod.POST)
    public ResultJSON updateChapterContent(){
        double startTime,endTime;
        startTime = System.currentTimeMillis();
        List<Chapter> chapterList = chapterService.getAll();
        for(int i=0;i<chapterList.size();i++) {
            if(chapterList.get(i).getHref()!=null && chapterList.get(i).getContent() == null){
                Spider.create(new InsertChapterContentProcessor(chapterList.get(i)))
                        .addUrl(chapterList.get(i).getHref())
                        .thread(1)
                        .addPipeline(insertChapterContentPipeLine)
                        //利用布隆过滤器设置网页去重
//                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(1000000)))
                        .run();
            }
            System.out.println("完成第 " + (i+1) + " 个章节的爬取操作");
        }
        endTime = System.currentTimeMillis();
        System.out.println("爬取结束，耗时约" + ((endTime - startTime) / 1000) + "秒");
        return ResultJSON.SUCCESS();
    }

    @RequestMapping(value = "/bookCoverImgCrawler",method = RequestMethod.POST)
    public ResultJSON bookCoverImgCrawler(){
        double startTime,endTime;
        startTime = System.currentTimeMillis();
        List<Book> bookList = bookService.getAllBooks();
//        for(int i=0;i<bookList.size();i++) {
        for(int i=0;i<bookList.size();i++) {
            String imgPath = bookList.get(i).getWebCoverImgPath();
            if(imgPath!=null){
                try {
                    // 创建httpclient实例
                    CloseableHttpClient httpclient = HttpClients.createDefault();
                    // 创建httpget实例
                    HttpGet httpget = new HttpGet(bookList.get(i).getWebCoverImgPath());
                    // 执行get请求
                    CloseableHttpResponse response = httpclient.execute(httpget);
                    HttpEntity entity = response.getEntity();
                    // 获取返回实体
                    String content = EntityUtils.toString(entity, "utf-8");
                    // 解析网页 得到文档对象
                    Document doc = Jsoup.parse(content);
                    //设置书本封面图片本地路径
                    HttpGet pictureHttpGet = new HttpGet(bookList.get(i).getWebCoverImgPath());
                    CloseableHttpResponse pictureResponse = httpclient.execute(pictureHttpGet);
                    HttpEntity pictureEntity = pictureResponse.getEntity();
                    InputStream inputStream = pictureEntity.getContent();
                File imgFile = new File("C:\\Users\\Administrator\\Desktop\\Imooc\\vue-crawler\\public\\static\\images\\cover\\" + bookList.get(i).getBookId()+ ".jpg");
                bookList.get(i).setLocalCoverImgPath("/static/images/cover/" + bookList.get(i).getBookId() + ".jpg");
//                bookList.get(i).setLocalCoverImgPath(Base64Util.encode("/static/images/cover/" + bookList.get(i).getAuthor()+"_"+bookList.get(i).getName() + ".jpg"));
//                Base64Util.encode(img)
                if(!imgFile.exists())
                    FileUtils.copyToFile(inputStream, imgFile);
                    pictureResponse.close(); // pictureResponse关闭
                    httpclient.close(); // httpClient关闭
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("完成第 " + (i+1) + " 本书封面图片的爬取操作");
        }
        bookService.updateAll(bookList);
        endTime = System.currentTimeMillis();
        System.out.println("爬取结束，耗时约" + ((endTime - startTime) / 1000) + "秒");
        return ResultJSON.SUCCESS();
    }


    /**
     * 将输入流转换为字节数组
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    /**
     * @Title: GetImageStrFromPath
     * @Description: TODO(将一张本地图片转化成Base64字符串)
     * @param imgPath
     * @return
     */
    public static String GetImageStrFromPath(String imgPath) {
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgPath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        return encoder.encode(data);
    }
}
