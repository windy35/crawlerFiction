package com.wen.crawler.config;

import com.wen.crawler.dao.LoginTokenDao;
import com.wen.crawler.dao.UsersDao;
import com.wen.crawler.model.HostHolder;
import com.wen.crawler.model.LoginToken;
import com.wen.crawler.model.Users;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 拦截器
 * @ 用来判断用户的
 *1. 当preHandle方法返回false时，从当前拦截器往回执行所有拦截器的afterCompletion方法，再退出拦截器链。也就是说，请求不继续往下传了，直接沿着来的链往回跑。
 2.当preHandle方法全为true时，执行下一个拦截器,直到所有拦截器执行完。再运行被拦截的Controller。然后进入拦截器链，运
 行所有拦截器的postHandle方法,完后从最后一个拦截器往回执行所有拦截器的afterCompletion方法.
 */

// （把普通pojo实例化到spring容器中，相当于配置文件中的
@Component
public class PassportInterceptor implements HandlerInterceptor{

    @Autowired
    LoginTokenDao loginTokenDao;

    @Autowired
    UsersDao usersDao;

    @Autowired
    HostHolder hostHolder;

    //判断然后进行用户拦截
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ticket = null;
        if(request.getCookies() != null){
            for(Cookie cookie : request.getCookies()){
                if(cookie.getName().equals("ticket")){
                    ticket = cookie.getValue();
                    break;
                }
            }
        }

        if(ticket != null ){
            LoginToken loginToken  = loginTokenDao.selectByToken(ticket);
            if(loginToken == null || loginToken.getExpired().before(new Date()) || !loginToken.isStatus()){
                return true;
            }
            Users user = usersDao.getOne(loginToken.getUsersId());
            hostHolder.setUsers(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //就是为了能够在渲染之前所有的freemaker模板能够访问这个对象user，就是在所有的controller渲染之前将这个user加进去
        if(hostHolder.getUsers() != null){
            //这个其实就和model.addAttribute一样的功能，就是把这个变量与前端视图进行交互 //就是与header.html页面的user对应
            request.setAttribute("usersInfo",hostHolder.getUsers());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();   //当执行完成之后呢需要将变量清空
    }
}
