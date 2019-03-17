package com.qyk.Jupyter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class HomePageController {
    @RequestMapping("/login")
    public String toLoginPage(){
        return "loginPage";
    }

    @RequestMapping("/loginCheck")
    public String login(HttpSession session, String name, String password, Map<String, Object> map){
        // 测试用！待完善
        if(name.equals(password)){
            session.setAttribute("username", name);
            return "coursePage";
        } else {
            map.put("error", "用户名密码不匹配或账户已被停用！请联系管理员！");
            return "loginPage";
        }
    }

    @RequestMapping("/experiment")
    public String toExperimentPage(Map<String, Object> map, String id){
        // 测试用！根据不同的实验id号返回对应的实验页面
        return "experimentPage";
    }
}
