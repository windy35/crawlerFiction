package com.wen.crawler.controller;

import com.wen.crawler.Tools.FileTool;
import com.wen.crawler.Tools.JpaPageHelper;
import com.wen.crawler.Tools.JpaPageInfo;
import com.wen.crawler.dao.ResultJSON;
import com.wen.crawler.model.Notice;
import com.wen.crawler.model.Users;
import com.wen.crawler.service.NoticeService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    NoticeService noticeService;

    @RequestMapping(value = "/getOne/{id}",method = RequestMethod.GET)
    public ResultJSON getOne(@PathVariable String id){
        ResultJSON rs = new ResultJSON();
        rs.setCode(200);
        Notice notice = noticeService.getById(Long.parseLong(id));
        rs.add("notice",notice);
        rs.setMsg("查询成功");
        return rs;
    }
    @RequestMapping(value = "/getAllNotice",method = RequestMethod.GET)
    public ResultJSON getAllNotice(){
        ResultJSON rs = new ResultJSON();
        rs.setCode(200);
        List<Notice> noticeList = noticeService.getAll();
        rs.add("noticeList",noticeList);
        return rs;
    }
    @RequestMapping(value = "/insertOne",method = RequestMethod.POST)
    public ResultJSON insertOne(@ModelAttribute Notice notice, @RequestPart("files")
            MultipartFile[] files, HttpSession httpSession){
        Users users = (Users) httpSession.getAttribute("user");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMDDHHmmss");
        if(files.length>0){
            String targetPath = "C:\\Users\\Administrator\\Desktop\\Imooc\\vue-crawler\\public\\static\\files\\noticeFile\\";
            String attachmentHref = "";
            String attachmentName = "";
            File targetFile = new File(targetPath);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            for(MultipartFile file:files){
                String tempName = sdf.format(new Date())+users.getUsersId()+file.getOriginalFilename();
                attachmentHref = attachmentHref+"\\static\\files\\noticeFile\\"+tempName+"|";
                attachmentName = attachmentName+file.getOriginalFilename()+"|";
                try {
                    FileUtils.copyInputStreamToFile(file.getInputStream(),new File(targetPath+tempName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            notice.setAttachmentHref(attachmentHref);
            notice.setAttachmentName(attachmentName);
        }
        ResultJSON rs = new ResultJSON();
        rs.setCode(200);
        notice.setSetter(users.getUserName());
        notice.setReleaseTime(new Date());
        notice.setLastUpdateTime(notice.getReleaseTime());
        noticeService.insertOne(notice);
        rs.setMsg("增加通知信息成功");
        return rs;
    }
    @RequestMapping(value = "/deleteOne/{id}",method = RequestMethod.DELETE)
    public ResultJSON deleteOne(@PathVariable String id){
        ResultJSON rs = new ResultJSON();
        rs.setCode(200);
        Notice notice = noticeService.getById(Long.parseLong(id));
        if(!"".equals(notice.getAttachmentHref())){
            String[] arr = notice.getAttachmentHref().split("\\|");
            for(String fileName:arr){
                FileTool.deleteFile("C:\\Users\\Administrator\\Desktop\\Imooc\\vue-crawler\\public\\static\\files\\noticeFile"+fileName);
            }
        }
        noticeService.deleteOne(notice);
        rs.setMsg("删除通知信息成功");
        return rs;
    }

    @RequestMapping(value = "/deleteAll/{ids}",method = RequestMethod.DELETE)
    public ResultJSON deleteAll(@PathVariable String ids){
        String[] idS= ids.split(",");
        ResultJSON rs = new ResultJSON();
        rs.setCode(200);
        for(String id:idS){
            Notice notice = noticeService.getById(Long.parseLong(id));
            if(null!=notice.getAttachmentHref()){
                String[] arr = notice.getAttachmentHref().split("\\|");
                for(String fileName:arr){
                    FileTool.deleteFile("C:\\Users\\Administrator\\Desktop\\Imooc\\vue-crawler\\public\\static\\files\\noticeFile"+fileName);
                }
            }
            noticeService.deleteOne(notice);
        }
        rs.setMsg("删除通知信息成功");
        return rs;
    }

    @RequestMapping(value = "/getAllNoticeByPageHelper",method = RequestMethod.GET)
    public ResultJSON getAllTypeByPageHelper(@RequestParam(required = false,defaultValue = "1") String pageNum, @RequestParam(required = false,defaultValue = "10") String limit, @RequestParam(required = false,defaultValue = "") String timestamp){
        ResultJSON rs = new ResultJSON();
        rs.setCode(200);
        List<Notice> noticeList = noticeService.getAll();
        JpaPageHelper jpaPageHelper = new JpaPageHelper();
        JpaPageInfo pageInfo = jpaPageHelper.SetStartPage(noticeList,Integer.parseInt(pageNum),Integer.parseInt(limit));
        rs.setMsg("查询成功");
        rs.add("noticeList",noticeList).add("page",pageInfo);
        return rs;
    }

    @RequestMapping(value = "/updateOne",method = RequestMethod.PUT)
    public ResultJSON updateOne(@ModelAttribute Notice notice){
        ResultJSON rs = new ResultJSON();
        rs.setCode(200);
        noticeService.updateOne(notice);
        rs.setMsg("修改通知信息成功");
        return rs;
    }
}
