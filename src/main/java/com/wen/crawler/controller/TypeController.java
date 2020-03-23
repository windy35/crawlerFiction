package com.wen.crawler.controller;

import com.wen.crawler.Tools.JpaPageHelper;
import com.wen.crawler.Tools.JpaPageInfo;
import com.wen.crawler.dao.ResultJSON;
import com.wen.crawler.model.Type;
import com.wen.crawler.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value = "/type")
public class TypeController {
    @Autowired
    TypeService typeService;

    @RequestMapping(value = "/getAllTypes",method = RequestMethod.GET)
    public ResultJSON getAllTypes(){
        ResultJSON rs = new ResultJSON();
        rs.setCode(200);
        List<Type> typeList = typeService.getAll();
        rs.setMsg("查询成功");
        rs.add("typeList",typeList);
        return rs;
    }

    @RequestMapping(value = "/deleteOne/{id}",method = RequestMethod.DELETE)
    public ResultJSON deleteOne(@PathVariable String id){
        ResultJSON rs = new ResultJSON();
        rs.setCode(200);
        Type type = typeService.getById(Long.parseLong(id));
         typeService.deleteOne(type);
         rs.setMsg("删除类型信息成功");
        return rs;
    }

    @RequestMapping(value = "/deleteAll/{ids}",method = RequestMethod.DELETE)
    public ResultJSON deleteAll(@PathVariable String ids){
        String[] idS= ids.split(",");
        ResultJSON rs = new ResultJSON();
        rs.setCode(200);
        for(String id:idS){
            Type type = typeService.getById(Long.parseLong(id));
            typeService.deleteOne(type);
        }
        rs.setMsg("删除类型信息成功");
        return rs;
    }

    @RequestMapping(value = "/getAllTypeByPageHelper",method = RequestMethod.GET)
    public ResultJSON getAllTypeByPageHelper(@RequestParam(required = false,defaultValue = "1") String pageNum, @RequestParam(required = false,defaultValue = "10") String limit, @RequestParam(required = false,defaultValue = "") String timestamp){
        ResultJSON rs = new ResultJSON();
        rs.setCode(200);
        List<Type> typeList = typeService.getAll();
        JpaPageHelper jpaPageHelper = new JpaPageHelper();
        JpaPageInfo pageInfo = jpaPageHelper.SetStartPage(typeList,Integer.parseInt(pageNum),Integer.parseInt(limit));
        rs.setMsg("查询成功");
        rs.add("typeList",typeList).add("page",pageInfo);
        return rs;
    }
}
