package com.wen.crawler.controller;

import com.wen.crawler.Tools.JpaPageHelper;
import com.wen.crawler.Tools.JpaPageInfo;
import com.wen.crawler.dao.ResultJSON;
import com.wen.crawler.model.Book;
import com.wen.crawler.model.BookCase;
import com.wen.crawler.model.Chapter;
import com.wen.crawler.model.Type;
import com.wen.crawler.service.BookCaseService;
import com.wen.crawler.service.BookService;

import com.wen.crawler.service.ChapterService;
import com.wen.crawler.service.TypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/books")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private BookCaseService bookCaseService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;
    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);


    @RequestMapping(value = "/setBookInfo",method = RequestMethod.POST)
    public ResultJSON setBookInfo(){
        ResultJSON rs = new ResultJSON();
        rs.setCode(200);
        List<Book> bookList = bookService.getAll();
        for(Book book:bookList){
            redisTemplate.opsForValue().set("book"+book.getBookId(),book);
        }
        return rs;
    }

    @RequestMapping(value = "/getBookInfo/{id}",method = RequestMethod.GET)
    public ResultJSON getBookInfo(@PathVariable String id){
        ResultJSON rs = new ResultJSON();
        if(Long.parseLong(id)<=0){
            rs.setCode(500);
            rs.setMsg("查询失败");
        }
        else{
            rs.setCode(200);
            rs.setMsg("查询成功");
            rs.add("bookInfo",redisTemplate.opsForValue().get("book"+id));
        }
        return rs;
    }

    @RequestMapping(value = "/setBookChapter",method = RequestMethod.POST)
    public ResultJSON setBookChapter(){
        ResultJSON rs = new ResultJSON();
        rs.setCode(200);
        List<Book> bookList = bookService.getAll();
        for(Book book:bookList){
            List<Chapter> chapterList = chapterService.getByBookId(book.getBookId());
            List<Map> mapList = new ArrayList<>();
            for(Chapter chapter:chapterList)
            {
                Map map = new HashMap<String,String>();
                map.put("id",chapter.getId());
                map.put("title",chapter.getTitle());
                mapList.add(map);
            }
            redisTemplate.opsForValue().set("book"+book.getBookId()+"Chapter",mapList);
        }
        return rs;
    }

    @RequestMapping(value = "/getOneBookChapterList/{id}",method = RequestMethod.GET)
    public ResultJSON getOneBookChapterList(@PathVariable String id){
        ResultJSON rs = new ResultJSON();
        if(Long.parseLong(id)<=0){
            rs.setCode(500);
            rs.setMsg("查询失败");
        }
        else{
            rs.setCode(200);
            rs.setMsg("查询成功");
            rs.add("bookInfo",redisTemplate.opsForValue().get("book"+id));
            rs.add("chapterList",redisTemplate.opsForValue().get("book"+id+"Chapter"));
        }
        return rs;
    }

    @RequestMapping(value = "/getAllBooksNotLimit",method = RequestMethod.GET)
    public ResultJSON getAllBooksNotLimit(){
        ResultJSON rs = new ResultJSON();
        rs.setCode(200);
        List<Book> bookList = bookService.getAll();
        rs.setMsg("查询成功");
        rs.add("bookList",bookList);
        return rs;
    }

    @RequestMapping(value = "/getAllBooks",method = RequestMethod.GET)
    public ResultJSON getAllBooks(@RequestParam(required = false,defaultValue = "1") String pageNum, @RequestParam(required = false,defaultValue = "10") String limit, @RequestParam(required = false,defaultValue = "") String timestamp){
        ResultJSON rs = new ResultJSON();
        rs.setCode(200);
        List<Book> bookList = bookService.getAllBooks();
       JpaPageHelper jpaPageHelper = new JpaPageHelper();
       JpaPageInfo pageInfo = jpaPageHelper.SetStartPage(bookList,Integer.parseInt(pageNum),Integer.parseInt(limit));
        rs.setMsg("查询成功");
        rs.add("bookList",bookList).add("page",pageInfo);
        return rs;
    }

    @RequestMapping(value = "/getAllBooksLimit",method = RequestMethod.GET)
    public ResultJSON getAllBooksLimit(@RequestParam(required = false,defaultValue = "0") String offset, @RequestParam(required = false,defaultValue = "10") String limit, @RequestParam(required = false,defaultValue = "") String timestamp){
        ResultJSON rs = new ResultJSON();
        rs.setCode(200);
        List<Book> bookList = bookService.getAllBooksLimit(Integer.parseInt(offset),Integer.parseInt(limit));
        rs.setMsg("查询成功");
        rs.add("bookList",bookList);
        return rs;
    }

    @RequestMapping(value = "/getByType/{type}",method = RequestMethod.GET)
    public ResultJSON getByType(@PathVariable("type") String type,@RequestParam(required = false,defaultValue = "1") String pageNum, @RequestParam(required = false,defaultValue = "10") String limit, @RequestParam(required = false,value = "") String timestamp){
        ResultJSON rs = new ResultJSON();
        rs.setCode(200);
        if(type.equals("") || type == null){
            rs.setMsg("请输入查询内容");
        }else{
            List<Book> bookList = bookService.getByType(type);
            JpaPageHelper jpaPageHelper = new JpaPageHelper();
            JpaPageInfo pageInfo = jpaPageHelper.SetStartPage(bookList,Integer.parseInt(pageNum),Integer.parseInt(limit));
            rs.setMsg("查询成功");
            rs.add("bookList",bookList).add("page",pageInfo);
        }
        return rs;
    }

    @RequestMapping(value = "/getByTypeLimit/{type}",method = RequestMethod.GET)
    public ResultJSON getByTypeLimit(@PathVariable("type") String type,@RequestParam(required = false,defaultValue = "0") String offset, @RequestParam(required = false,defaultValue = "10") String limit, @RequestParam(required = false,value = "") String timestamp){
        ResultJSON rs = new ResultJSON();
        rs.setCode(200);
        if(type.equals("") || type == null){
            rs.setMsg("请输入查询内容");
        }else{
            List<Book> bookList = bookService.getByTypeLimit(type,Integer.parseInt(offset),Integer.parseInt(limit));
            rs.setMsg("查询成功");
            rs.add("bookList",bookList);
        }
        return rs;
    }


    @RequestMapping(value = "/getEachByTypeLimit",method = RequestMethod.GET)
    public ResultJSON getEachByTypeLimit(@RequestParam(required = false,defaultValue = "0") String offset, @RequestParam(required = false,defaultValue = "10") String limit, @RequestParam(required = false,value = "") String timestamp){
        ResultJSON rs = new ResultJSON();
        rs.setCode(200);
        List<Type> typeList = typeService.getAll();
        List<List<Book>> bookList = new ArrayList<>();
        for(Type type:typeList){
            bookList.add((List<Book>)getByTypeLimit(type.getName(),offset,limit,timestamp).getInfo().get("bookList"));
        }
        rs.add("bookList",bookList);
        return rs;
    }


    @RequestMapping(value = "/getOneBookDetailInfo/{bookId}",method = RequestMethod.GET)
    public ResultJSON getOneBookDetailInfo(@PathVariable String bookId){
        ResultJSON rs = new ResultJSON();
        rs.setCode(200);
        Optional<Book> book = bookService.getOneBookBasicInfo(Long.parseLong(bookId));
        List<Chapter> chapter = chapterService.getByBookId(Long.parseLong(bookId));
        rs.add("bookBasicInfo",book);
        rs.add("chapterList",chapter);
        return rs;
    }

    @RequestMapping(value = "/getOneBookBasicInfo/{bookId}",method = RequestMethod.GET)
    public ResultJSON getOneBookBasicInfo(@PathVariable String bookId){
        ResultJSON rs = new ResultJSON();
        rs.setCode(200);
        Optional<Book> book = bookService.getOneBookBasicInfo(Long.parseLong(bookId));
        rs.add("bookBasicInfo",book);
        return rs;
    }

    @RequestMapping(value = "/updateChapterCount",method = RequestMethod.PUT)
    public void updateChapterCount(){
        List<Book> bookList = bookService.getAllBooks();
        for(Book book:bookList){
            book.setChapterCount(bookService.getOneBookChapterCount(book.getBookId()));
        }
        bookService.updateAll(bookList);
    }

    @RequestMapping(value = "/addBookHeat/{bookId}",method = RequestMethod.PUT)
    public void addBookHeat(@PathVariable String bookId){
        Optional<Book> book = bookService.getOneBookBasicInfo(Long.parseLong(bookId));
        Book book1 = book.get();
        book1.setHeat(book1.getHeat());
        bookService.updateOne(book1);
    }
    @RequestMapping(value = "/deleteOne/{id}",method = RequestMethod.DELETE)
    public ResultJSON deleteOne(@PathVariable String id){
        ResultJSON rs = new ResultJSON();
        List<BookCase>bookCaseList = bookCaseService.getByBookId(Long.parseLong(id));
        List<Chapter>chapterList = chapterService.getByBookId(Long.parseLong(id));
        if(bookCaseList.size()==0){
//            bookService.deleteById(Long.parseLong(id));
//            for(Chapter chapter:chapterList)
//                chapterService.deleteOne(chapter);
            rs.setCode(200);
            rs.setMsg("删除书本信息成功");
        }else{
            rs.setCode(500);
            rs.setMsg("无法删除关联了书签的数据");
        }
        return rs;
    }

    @RequestMapping(value = "/deleteAll/{ids}",method = RequestMethod.DELETE)
    public ResultJSON deleteAll(@PathVariable String ids){
        String[] idS= ids.split(",");
        ResultJSON rs = new ResultJSON();
        rs.setCode(200);
        rs.setMsg("删除书本信息成功");
        for(String id:idS){
            List<BookCase>bookCaseList = bookCaseService.getByBookId(Long.parseLong(id));
            List<Chapter>chapterList = chapterService.getByBookId(Long.parseLong(id));
            if(bookCaseList.size()==0){
//            bookService.deleteById(Long.parseLong(id));
//            for(Chapter chapter:chapterList)
//                chapterService.deleteOne(chapter);
            }else{
                rs.setCode(500);
                rs.setMsg("无法删除关联了书签的数据");
            }
        }
        return rs;
    }
}
