package com.fc.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.fc.util.MyUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import redis.clients.jedis.Jedis;

import java.util.List;

public class LoginInterceptor extends HandlerInterceptorAdapter{

    private List<String> excludedUrls;

    @Autowired
    private Jedis jedis;
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        String requestUri = request.getRequestURI();


        for (String s : excludedUrls) {
            if (requestUri.endsWith(s)) {
                return true;
            }
        }

        Integer uid =  null;
        Cookie cookie= MyUtil.getCookieByName(request, "JSESSIONID");
        String uidStr = jedis.get(cookie.getValue());
        if (!StringUtils.isEmpty(uidStr)){
            uid = Integer.parseInt(uidStr);
            request.getSession().setAttribute("uid",uid);
        }
        if(uid == null){
            request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    public List<String> getExcludedUrls() {
        return excludedUrls;
    }

    public void setExcludedUrls(List<String> excludedUrls) {
        this.excludedUrls = excludedUrls;
    }

}
