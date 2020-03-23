package com.wen.crawler.controller;

import com.wen.crawler.dao.ResultJSON;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {
    /**
     * 下载网站的快捷方式图标
     * @return
     */
    @RequestMapping(value = "/downloadShortCut",method = RequestMethod.GET)
    public ResultJSON downloadShortCut(HttpServletResponse response){
        ResultJSON rs = new ResultJSON();
        String realFileName = "小说网首页.url";//不加.url扩展名无法识别文件
        String templateContent = "[InternetShortcut]" + "\n" + "URL= http://localhost:8080/";//快捷方式内容

        rs.setCode(200);
        FileInputStream fis = null;//文件输入流
        BufferedInputStream bis = null;//文件输入流的缓冲
        OutputStream outputStream = null;
        try {
            Resource resource = new ClassPathResource("");
            File shortCutFile = new File(resource.getFile().getAbsolutePath()+"/static/files/"+realFileName);

            //如果快捷方式文件不存在在服务器上创建快捷方式的文件
            if(!shortCutFile.exists()){
                FileOutputStream fileoutputstream = new FileOutputStream(resource.getFile().getAbsolutePath()+"/static/files/"+realFileName);//建立文件输出流
                byte tag_bytes[] = templateContent.getBytes();
                fileoutputstream.write(tag_bytes);
                fileoutputstream.close();
            }
            //设置response的Header
            response.reset();
            realFileName = new String(java.net.URLEncoder.encode(realFileName, "UTF-8"));//中文文件名转换为UTF-8编码
            response.addHeader("Content-Disposition", "attachment;filename="+ realFileName);
            response.setContentType("application/x-download;charset=utf-8");//不同类型的文件对应不同的MIME类型
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
            if(shortCutFile.exists()){
                byte[] buffer = new byte[1024];
                fis = new FileInputStream(shortCutFile);//文件输入流
                bis = new BufferedInputStream(fis);//文件输入流的缓冲
                outputStream = new BufferedOutputStream(response.getOutputStream());
                while (bis.read(buffer) > 0) {
                    outputStream.write(buffer);
                }
                rs.setMsg("下载成功");
            }else{
                rs.setMsg("下载的文件为空！");
            }
        }catch (IOException e){
            e.printStackTrace();
            rs.setCode(500);
            rs.setMsg("文件下载过程中出现异常！");
            e.printStackTrace();
        }finally{
            if(bis!=null){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return rs;
    }

    /**
     * 多文件或者单文件上传操作
     * @param files
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "uploadfile",method = RequestMethod.GET)
    public ResultJSON uploadfiile(@RequestParam("file") List<MultipartFile> files) {
        ResultJSON rs = new ResultJSON();
        String str = "";
        Resource resource = new ClassPathResource("");
        rs.setCode(200);//成功的状态码为200
        for(int i=0;i<files.size();i++){
            try{
                if(!files.get(i).isEmpty()){
                    System.out.println(resource.getFile().getAbsolutePath()+"/static/file/"+System.currentTimeMillis()+files.get(i).getOriginalFilename());
                    FileUtils.copyInputStreamToFile(files.get(i).getInputStream(),new File(resource.getFile().getAbsolutePath()+"/static/file/",System.currentTimeMillis()+files.get(i).getOriginalFilename()));
                    rs.add("SUCCESS_MSG_fileMsg"+i+"：","文件名为："+files.get(i).getOriginalFilename()+" 的文件上传成功");
                    rs.setMsg("文件上传成功");
                }else{
                    rs.add("ERROR_MSG_fileMsg"+i+"：","文件名为："+files.get(i).getOriginalFilename()+" 的文件为空_上传失败");
                    rs.setMsg("部分或全部文件上传失败,请查看提示信息！");
                }
            }catch (IOException e){
                rs.setCode(500);
                rs.setMsg("文件上传过程中出现异常");
                e.printStackTrace();
            }

        }
        return rs;
    }
}
