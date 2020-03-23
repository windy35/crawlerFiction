package com.wen.crawler.controller;

import com.wen.crawler.Tools.StringDateInterconversion;
import com.wen.crawler.dao.ResultJSON;
import com.wen.crawler.model.Book;
import com.wen.crawler.model.BookCase;
import com.wen.crawler.model.Users;
import com.wen.crawler.service.BookCaseService;
import com.wen.crawler.service.BookService;
import com.wen.crawler.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/bookCase")
public class BookCaseController {
    @Autowired
    BookCaseService bookCaseService;
    @Autowired
    BookService bookService;
    @Autowired
    UsersService usersService;
    @RequestMapping(value = "/insertOne",method = RequestMethod.POST)
    public ResultJSON insertOne(@RequestBody BookCase bookCase, HttpSession httpSession){
        Users users = (Users) httpSession.getAttribute("user");
        ResultJSON rs = new ResultJSON();
        rs.setCode(200);
        boolean existBooksign = bookCaseService.isExistBook(bookCase.getUsersId(),bookCase.getBookId());
        boolean zjml_sign = "章节目录".equals(bookCase.getTagChapterName());
        if(!existBooksign){
            Book book = bookService.getOneBookBasicInfo(bookCase.getBookId()).get();
            long count = users.getBookCount();
            if(count<20){
                Date now = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
                bookCase.setTagLastUpdateTime(StringDateInterconversion.strToDateLong(dateFormat.format(now)));
                bookCaseService.insertOne(bookCase);
                users.setBookCount(count+1);
                usersService.updateOne(users);
                rs.setMsg("成功加入书架");
            }else{
                rs.setMsg("书架已满请先删除部分书签");
            }
        }
        if(existBooksign && !zjml_sign){
            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
            bookCaseService.updateOne(bookCase.getUsersId(),bookCase.getBookId(),bookCase.getTagChapterName(),bookCase.getTagChapterId(),dateFormat.format(now));
            rs.setMsg("更新书签信息成功");
        }
        if(existBooksign && zjml_sign){
            rs.setMsg("已在其他章节中插入书签信息");
        }
        httpSession.setAttribute("user",users);
        return rs;
    }

    @RequestMapping(value = "/deleteOne/{id}",method = RequestMethod.DELETE)
    public ResultJSON deleteOne(@PathVariable String id,HttpSession httpSession){
        ResultJSON rs = new ResultJSON();
        Users users = (Users)httpSession.getAttribute("user");
        rs.setCode(200);
        long count = users.getBookCount();
        users.setBookCount(count-1);
        bookCaseService.deleteById(Long.parseLong(id));
        usersService.updateOne(users);
        httpSession.setAttribute("user",users);
        rs.setMsg("删除书签成功");
        return rs;
    }


    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    public ResultJSON getAll(){
        ResultJSON rs = new ResultJSON();
        rs.setCode(200);
        List<BookCase> bookCaseList = bookCaseService.getAll();
        rs.setMsg("查询成功");
        rs.add("bookCaseList",bookCaseList);
        return rs;
    }

    @RequestMapping(value = "/getByUsersId/{usersId}",method = RequestMethod.GET)
    public ResultJSON getByUsersId(@PathVariable String usersId){
        ResultJSON rs = new ResultJSON();
        rs.setCode(200);
        List<BookCase> bookCaseList = bookCaseService.getByUsersId(Integer.parseInt(usersId));
        rs.setMsg("查询成功");
        rs.add("bookCaseList",bookCaseList);
        return rs;
    }

}
