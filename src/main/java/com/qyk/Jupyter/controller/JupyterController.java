package com.qyk.Jupyter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "jupyter")
public class JupyterController {

    // 心跳机制，检测前端jupyter页面是否正常/异常关闭
    @RequestMapping(value = "keeplive")
    @ResponseBody
    public String keeplive(HttpSession session) {
        String token = (String) session.getAttribute("token");
        System.out.println(token);

        return "hello!";
    }
}
