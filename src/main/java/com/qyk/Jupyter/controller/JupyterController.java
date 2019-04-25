package com.qyk.Jupyter.controller;

import com.qyk.Jupyter.service.JupyterService;
import com.qyk.Jupyter.utils.JwtUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping(value = "jupyter")
public class JupyterController {

    @Autowired
    private JupyterService jupyterService;

    private static final Logger logger = Logger.getLogger(JupyterController.class);

    /**
     * 进入jupyter设计器
     * @param id jupyter文件 id
     * @param token 用户认证的JWT-token(可能放在请求头中？)
     * @return jupyter编辑器页面
     */
    @RequestMapping(value = "")
    public String openJupyter(Integer id, String token){
        Map<String, Object> userInfo;

        // 从远端获取文件信息(name/url/type/orig_id)
        Map<String, Object> fileInfo = jupyterService.getFileInfo(id);

        // 解密用户信息
        try {
            userInfo = JwtUtils.verifyToken(token);
        } catch (Exception e) {
            logger.info("用户鉴权失败... token=" + token);
            // 返回错误页面
            return " ";
        }


        return "";
    }






    /* ------------以下为Demo版本使用的-------------- */
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
    /* ------------以下为Demo版本使用的-------------- */

}
