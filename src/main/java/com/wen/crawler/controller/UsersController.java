package com.wen.crawler.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.wen.crawler.Tools.*;
import com.wen.crawler.dao.ResultJSON;
import com.wen.crawler.model.LoginToken;
import com.wen.crawler.model.Users;
import com.wen.crawler.service.LoginTokenService;
import com.wen.crawler.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/users")
public class UsersController {
    @Autowired
    UsersService usersService;
    @Autowired
    LoginTokenService loginTokenService;
    @Autowired
    private DefaultKaptcha captchaProducer;
    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);


    /**
     * 登录验证码Session
     */
    private static final String LOGIN_VALIDATE_CODE = "login_validate_code";


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResultJSON logout(HttpServletRequest httpServletRequest){
        Cookie[] cookies = httpServletRequest.getCookies();
        String ticket = "";
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("ticket")){
                ticket = cookie.getValue();
            }
        }
        loginTokenService.updateTicketStatus(false,ticket);
        return  ResultJSON.SUCCESS();
    }

    @RequestMapping(value = "/selectByName", method = RequestMethod.GET)
    public ResultJSON selectByName(@RequestParam(required = false,value = "userName")String userName){
        ResultJSON rs = new ResultJSON();
        Users users = usersService.selectByName(userName);
            if(users == null){
            rs.add("isExist",false);
            return rs;
        }
        rs.add("isExist",true);
        return rs;
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResultJSON register(@ModelAttribute Users user,@RequestParam String comfirmPassword,HttpServletResponse response){
        ResultJSON rs = new ResultJSON();
        if("".equals(user.getUserName())){
            rs.setCode(500);
            rs.setMsg("请输入用户名");
            return rs;
        }
        if("".equals(user.getPassword())){
            rs.setCode(500);
            rs.setMsg("请输入密码");
            return rs;
        }
        if("".equals(comfirmPassword)){
            rs.setCode(500);
            rs.setMsg("请再次输入密码");
            return rs;
        }
        if (user.getPassword().length()<2 || user.getPassword().length()>20){
            rs.setCode(500);
            rs.setMsg("请输入2-20位字符的用户名");
            return rs;
        }
        if(usersService.selectByName(user.getUserName())!=null){
            rs.setCode(500);
            rs.setMsg("用户名已存在");
            return rs;
        }
        int ls = MatchNewPwd(user.getPassword());
        if(ls<3){
            rs.setCode(500);
            rs.setMsg("密码要在数字、小写字母、大写字母以及英文状态输入的特殊字符中四选三");
            return rs;
        }
        if(!comfirmPassword.equals(user.getPassword())){
            rs.setCode(500);
            rs.setMsg("两次输入的密码不一致");
            return rs;
        }
        if(!"".equals(user.getPhone())){
            if(!"".equals(RegexMatchStr.getTargetStr("1[34578]\\d{9}",user.getPhone()))){
                rs.setCode(500);
                rs.setMsg("请输入合法手机号");
                return rs;
            }
        }
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        long id = usersService.insertOne(user);
        user.setUsersId(id);
        rs.setCode(200);
        rs.setMsg("注册成功");
        //注册完成生成token
        String token = addLoginToken(id);
        //从map中获得redis中的key
        Cookie cookie = new Cookie("ticket",token);
        cookie.setPath("/");
        response.addCookie(cookie);
        return rs;
    }


    /**
     * 设置登录用户的token信息
     * @param users_id
     * @return
     */
    public String addLoginToken(long users_id){
        LoginToken loginToken = new LoginToken();
        loginToken.setUsersId(users_id);
        Date nowDate = new Date();
        nowDate.setTime(3600*24*100 + nowDate.getTime());
        loginToken.setExpired(nowDate);
        loginToken.setStatus(true);
        loginToken.setToken(UUID.randomUUID().toString().replaceAll("_",""));
        loginToken.setLastLoginTime(new Date());
        loginTokenService.updateOne(loginToken);
        return loginToken.getToken();

    }

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public ResultJSON getUserInfo(@RequestBody(required = false) Users usersInfo){
        ResultJSON rs = new ResultJSON();
        System.out.println("getUserInfo："+usersInfo);
        rs.add("users",usersInfo);
        return rs;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultJSON login(HttpServletRequest request, HttpServletResponse response,@RequestParam(required = false,defaultValue = "0") String role, @RequestParam(required = false,defaultValue = "") String username, @RequestParam(required = false,defaultValue = "") String password, @RequestParam(required = false,defaultValue = "") String verificationCode,@RequestParam(required = false,defaultValue = "") String random){
        ResultJSON rs = new ResultJSON();
        String loginValidateCode = (String) request.getSession().getAttribute(LOGIN_VALIDATE_CODE);
//        if("".equals(username) || "".equals(password) || "".equals(verificationCode)){
        if("".equals(username) || "".equals(password)){
            rs.setCode(500);
            rs.setMsg("用户名密码或验证码不能为空！");
        }else{
            Users users = usersService.isExistUser(username,DigestUtils.md5DigestAsHex(password.getBytes()),Integer.parseInt(role));
            if(users == null) {
                rs.setCode(500);
                rs.setMsg("用户名密码或权限错误！");
            }else{
                if(verificationCode.equals(loginValidateCode)){
                    rs.setCode(500);
                    rs.setMsg("验证码错误！");
                }else{
                    rs.setCode(200);
                    rs.setMsg("登录成功！");
                    String token = addLoginToken(users.getUsersId());
                    Cookie cookie = new Cookie("ticket",token);
                    cookie.setPath("/");           //可在同一应用服务器内共享cookie
                    response.addCookie(cookie);
                    logger.info(users.getUserName()+"登录成功");
                    rs.add("user",users);
                }
            }
        }
        return rs;
    }

    @RequestMapping(value = {"/loginValidateCode"},method = RequestMethod.GET)
    public void loginValidateCode(HttpServletRequest request, HttpServletResponse response) throws Exception{
        vcCodeUtil.validateCode(request,response,captchaProducer,LOGIN_VALIDATE_CODE);
    }

    @RequestMapping(value = {"/alertPwd"},method = RequestMethod.PUT)
    public ResultJSON alertPwd(HttpSession httpSession,@RequestParam String inputPwd,@RequestParam String inputNewPwd,@RequestParam String inputComPwd) throws Exception{
        Users users = (Users)httpSession.getAttribute("user");
        ResultJSON rs = new ResultJSON();
        rs.setCode(200);
        if(!inputPwd.equals(users.getPassword())){
            rs.setMsg("输入的原密码不正确");
            return rs;
        }
        if(inputNewPwd.length()<6 || inputNewPwd.length()>20){
            rs.setMsg("请输入6-20的新密码");
            return rs;
        }
        if(MatchNewPwd(inputNewPwd)<3){
            rs.setMsg("新密码需要从数字、小写字母、大写字母以及英文状态输入的特殊字符中四选三");
            return rs;
        }
        if(!inputNewPwd.equals(inputComPwd)){
            rs.setMsg("两次输入的新密码不一致");
            return rs;
        }
        rs.setMsg("修改成功,请重新登录");
        users.setPassword(inputNewPwd);
        usersService.updateOne(users);
        return rs;
    }

    int MatchNewPwd(String NewPwd){
        int rs = 0;
        if(!"".equals(RegexMatchStr.getTargetStr("([a-z])+",NewPwd)))
            rs++;
        if(!"".equals(RegexMatchStr.getTargetStr("([0-9])+",NewPwd)))
            rs++;
        if(!"".equals(RegexMatchStr.getTargetStr("([A-Z])+",NewPwd)))
            rs++;
        if(!"".equals(RegexMatchStr.getTargetStr("([~'!@#,.$%^&*()-+_=:])+",NewPwd)))
            rs++;
        return rs;
    }

    @RequestMapping(value = "/deleteOne/{id}",method = RequestMethod.DELETE)
    public ResultJSON deleteOne(@PathVariable String id){
        ResultJSON rs = new ResultJSON();
        LoginToken loginToken = loginTokenService.getById(Long.parseLong(id));
        if(!loginToken.isStatus()){
            usersService.deleteById(Long.parseLong(id));
            rs.setCode(200);
            rs.setMsg("删除用户信息成功");
        }
        if(loginToken.isStatus()){
            usersService.deleteById(Long.parseLong(id));
            rs.setMsg("无法删除正在登陆的用户信息");
        }
        return rs;
    }

    @RequestMapping(value = "/deleteAll/{ids}",method = RequestMethod.DELETE)
    public ResultJSON deleteAll(@PathVariable String ids){
        String[] idS= ids.split(",");
        ResultJSON rs = new ResultJSON();
        rs.setCode(200);
        rs.setMsg("删除用户信息成功");
        for(String id:idS){
            LoginToken loginToken = loginTokenService.getById(Long.parseLong(id));
            if(!loginToken.isStatus()){
                usersService.deleteById(Long.parseLong(id));
            }
            if(loginToken.isStatus()){
                rs.setCode(500);
                rs.setMsg("无法删除部分正在登陆的用户信息");
            }
        }
        return rs;
    }

    @RequestMapping(value = "/getAllUsersByPageHelper",method = RequestMethod.GET)
    public ResultJSON getAllUsersByPageHelper(@RequestParam(required = false,defaultValue = "1") String pageNum, @RequestParam(required = false,defaultValue = "10") String limit, @RequestParam(required = false,defaultValue = "") String timestamp){
        ResultJSON rs = new ResultJSON();
        rs.setCode(200);
        List<Users> usersList = usersService.getAll();
        JpaPageHelper jpaPageHelper = new JpaPageHelper();
        JpaPageInfo pageInfo = jpaPageHelper.SetStartPage(usersList,Integer.parseInt(pageNum),Integer.parseInt(limit));
        rs.setMsg("查询成功");
        rs.add("usersList",usersList).add("page",pageInfo);
        return rs;
    }
}


