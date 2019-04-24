package com.qyk.Jupyter.controller;

import com.qyk.Jupyter.service.JupyterService;
import org.apache.log4j.Logger;
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

    private static final Logger logger = Logger.getLogger(JupyterController.class);

    // 心跳机制，检测前端jupyter页面是否正常/异常关闭
    @RequestMapping(value = "keeplive")
    @ResponseBody
    public String keeplive(HttpSession session) {
        String token = (String) session.getAttribute("token");
        Integer port = (Integer) session.getAttribute("port");
        System.out.println("token:" + token + "port:" + port);

        if(token == null || port==null){
            return "error!";
        }

        String jupyterList = jupyterService.getJupyterList();
        logger.info(jupyterList);

        jupyterService.keeplive(port.toString());

        return "hello!";
    }

    // 新建一个实验
    @RequestMapping(value = "addExperiment")
    public String addExperiment(String token, Integer id){

        return "";
    }

    // 新建一个作业
    @RequestMapping(value = "addWork")
    public String addWork(String token, Integer id){

        return "";
    }
}
