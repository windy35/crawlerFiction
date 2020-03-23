package com.wen.crawler.dao;

import java.util.HashMap;
import java.util.Map;

public class ResultJSON {
    private int code;//状态码
    private String msg;//提示信息
    private Map<String,Object> info = new HashMap<>();

    public ResultJSON() {
    }

    public ResultJSON(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultJSON(int code, String msg, Map<String, Object> info) {
        this.code = code;
        this.msg = msg;
        this.info = info;
    }

    public static ResultJSON SUCCESS(){
        ResultJSON rs = new ResultJSON();
        rs.setCode(200);
        rs.setMsg("处理成功");
        return rs;
    }

    public static ResultJSON FAIL(){
        ResultJSON rs = new ResultJSON();
        rs.setCode(500);
        rs.setMsg("处理失败");
        return rs;
    }


    public ResultJSON add(String key,Object value){
        this.getInfo().put(key,value);
        return this;
    }
    public ResultJSON add(String key,String value){
        this.getInfo().put(key,value);
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getInfo() {
        return info;
    }

    public void setInfo(Map<String, Object> info) {
        this.info = info;
    }


}
