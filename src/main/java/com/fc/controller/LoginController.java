package com.fc.controller;


import com.fc.model.User;
import com.fc.service.LoginService;
import com.fc.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private Jedis jedis;
    /**
     * 去注册和登录的页面
     * @return
     */
    @RequestMapping("/toLogin.do")
    public String toLogin(){
        return "login";
    }

    /**
     * 注册
     * @param user
     * @param repassword
     * @param model
     * @return
     */
    @RequestMapping(value = "/register.do",method = RequestMethod.POST)
    public String register(User user, String repassword,Model model){
        String result = loginService.register(user,repassword);
        if(result.equals("ok")){
            model.addAttribute("info","系统已经向你的邮箱发送了一封邮件哦，验证后就可以登录啦~");
            return "prompt/promptInfo";
        }else {
            model.addAttribute("register","yes");
            model.addAttribute("email",user.getEmail());
            model.addAttribute("error",result);
            return "login";
        }
    }


    /**
     * 登录
     * @param user
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/login.do",method = RequestMethod.POST)
    public String login(User user,Model model,HttpServletRequest request){
        Map<String,Object> map = loginService.login(user);
        if(map.get("status").equals("yes")){
            Cookie cookie= MyUtil.getCookieByName(request, "JSESSIONID");
            jedis.set(cookie.getValue(),map.get("uid").toString());
            jedis.expire(cookie.getValue(),60*60*60);
            request.getSession().setAttribute("uid",map.get("uid"));
            request.getSession().setAttribute("headUrl",map.get("headUrl"));
            return "redirect:toMyProfile.do";
        }else {
            model.addAttribute("email",user.getEmail());
            model.addAttribute("error",map.get("error"));
            return "login";
        }
    }


    /**
     * 激活
     * @param code
     * @param model
     * @return
     */
    @RequestMapping(value = "/activate.do")
    public String activate(String code,Model model){
        loginService.activate(code);

        model.addAttribute("info","您的账户已经激活成功，可以去登录啦~");
        return "prompt/promptInfo";
    }


    /**
     * 注销
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout.do",method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.removeAttribute("uid");
        return "redirect:toIndex.do";
    }
}


