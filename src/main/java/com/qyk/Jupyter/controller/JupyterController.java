package com.qyk.Jupyter.controller;

import com.qyk.Jupyter.service.JupyterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "jupyter")
public class JupyterController {

    @Autowired
    private JupyterService jupyterService;

    // 心跳机制，检测前端jupyter页面是否正常/异常关闭
    @RequestMapping(value = "keeplive")
    @ResponseBody
    public String keeplive(HttpSession session) {
        String token = (String) session.getAttribute("token");
        String port = ((Integer) session.getAttribute("port")).toString();
        System.out.println("token:" + token + "port:" + port);

        if(token == null){
            return "error!";
        }

        String jupyterList = jupyterService.getJupyterList();
        System.out.println(jupyterList);

        jupyterService.keeplive(port);

        return "hello!";
    }
}
