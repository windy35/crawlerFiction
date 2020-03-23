package com.wen.crawler.Tools.PageProcessor;

import com.wen.crawler.Tools.RegexMatchStr;
import com.wen.crawler.model.Book;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;


public class InsertBookInfoProcessor implements PageProcessor {

    private int typeid;
    private static int lastPageNum = 1;

    public InsertBookInfoProcessor(int typeid) {
        this.typeid = typeid;
    }

    // 抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    //爬虫配置信息设置
    private Site site = new Site()
            .setCharset("utf-8")    //设置编码
            .setSleepTime(2000)        //设置抓取间隔
            .setTimeOut(Integer.MAX_VALUE)       //设置超时时间
            .setRetrySleepTime(1000)    //设置重试时间
            .setRetryTimes(Integer.MAX_VALUE);       //设置重试次数

    @Override
    public Site getSite() {
        return site;
    }
    @Override
    public void process(Page page) {
        //爬取的a标签结果存储于arr数组
        List<Book> bookList = new ArrayList<Book>();
        String[] nameArr = page.getHtml().xpath("//*[@id=\"newscontent\"]/div[1]/ul/li/span[1]").all().toString().split(",");
        String[] authorArr = page.getHtml().xpath("//*[@id=\"newscontent\"]/div[1]/ul/li/span[3]").all().toString().split(",");
        lastPageNum = Integer.parseInt(page.getHtml().xpath("//*[@class=\"last\"]/text()").get());
        for(int i=0;i<nameArr.length;i++){
            //编译生成正则表达式并匹配
            Book book = new Book();
            //获取书本名称
            book.setName(RegexMatchStr.getTargetStr("[\\u4E00-\\u9FA5](.*?)",nameArr[i]));
            //获取书本链接
            book.setHref(RegexMatchStr.getTargetStr("a href=\"(.*?)\"",nameArr[i]).replace("a href=\"","").replace("\"",""));
            //获取作者名称
            book.setAuthor(RegexMatchStr.getTargetStr("[\\u4E00-\\u9FA5](.*?)",authorArr[i]));
            switch (typeid){
                case 1:book.setType("玄幻小说");break;
                case 2:book.setType("修真小说");break;
                case 3:book.setType("都市小说");break;
                case 4:book.setType("穿越小说");break;
                case 5:book.setType("网游小说");break;
                case 6:book.setType("科幻小说");break;
                default:book.setType("其它小说");
            }
            //复制图片到本地
//            try {
//                // 创建httpclient实例
//                CloseableHttpClient httpclient = HttpClients.createDefault();
//                // 创建httpget实例
//                HttpGet httpget = new HttpGet(book.getHref());
//                // 执行get请求
//                CloseableHttpResponse response = httpclient.execute(httpget);
//                HttpEntity entity = response.getEntity();
//                // 获取返回实体
//                String content = EntityUtils.toString(entity, "utf-8");
//
//                // 解析网页 得到文档对象
//                Document doc = Jsoup.parse(content);
//                // 获取指定的标签
//                Elements imgElements = doc.select("#fmimg > img");
//                Elements lastUpdateTimeElements = doc.select("#info > p:nth-child(4)");
//                Elements introductionElements = doc.select("#intro > p:nth-child(2)");
////                String ludt = lastUpdateTimeElements.get(0).toString().replace("最后更新：","").replace("<p>","").replace("</p>","");
//                //获取更新时间信息
////                book.setLastUpdateTime(StringDateInterconversion.strToDateLong(ludt));
//                //获取封面图片链接
//                book.setWebCoverImgPath(imgElements.get(0).attr("src"));
//                //获取书本简介
//                book.setIntroduction(introductionElements.get(0).text().replace("<p> ","").replace("</p>",""));
//                //设置书本封面图片本地路径
//                book.setLocalCoverImgPath("/static/img/cover/"+book.getAuthor()+"_"+book.getName()+".jpg");
//                HttpGet pictureHttpGet = new HttpGet(book.getWebCoverImgPath());
//                CloseableHttpResponse pictureResponse = httpclient.execute(pictureHttpGet);
//                HttpEntity pictureEntity = pictureResponse.getEntity();
//                InputStream inputStream = pictureEntity.getContent();
//
//                //用于获取项目路径
//                Resource resource = new ClassPathResource("");
//                // 使用 common-io 下载图片到本地，注意图片名不能重复
//                //如果封面图片不存在才创建
//                File imgFile = new File(resource.getFile().getAbsolutePath()+"/static/img/cover/" + book.getAuthor()+"_"+book.getName() + ".jpg");
//                if(!imgFile.exists())
//                    FileUtils.copyToFile(inputStream, imgFile);
//                pictureResponse.close(); // pictureResponse关闭
//                httpclient.close(); // httpClient关闭
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            bookList.add(book);
            System.out.println(book);
        }
        page.putField("bookList",bookList);
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public static int getLastPageNum() {
        return lastPageNum;
    }

    public static void setLastPageNum(int lastPageNum) {
        InsertBookInfoProcessor.lastPageNum = lastPageNum;
    }
}
