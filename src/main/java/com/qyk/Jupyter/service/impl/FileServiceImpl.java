package com.qyk.Jupyter.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.qyk.Jupyter.service.FileService;
import com.qyk.Jupyter.utils.ConfigurationFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service("fileService")
public class FileServiceImpl implements FileService {
    private static final Logger logger = Logger.getLogger(FileServiceImpl.class);

    @Override
    public JSONObject getFileInfo(Integer fileId) {
        // 读取访问接口
        String fileUrl = ConfigurationFactory.getInstance().getValue("file") + fileId;

        String result = "";

        // 发起访问请求
        try {
            URL url = new URL(fileUrl);    //把字符串转换为URL请求地址
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接

            //connection.setRequestProperty("设置请求头key", "请求头value");
            connection.connect();// 连接会话
            // 获取输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {// 循环读取流
                sb.append(line);
            }
            br.close();// 关闭流
            connection.disconnect();// 断开连接
            System.out.println(sb.toString());
            result = sb.toString();
        } catch (Exception e){
            e.printStackTrace();
            logger.info("连接失败！");
            return null;
        }

        JSONObject json = JSONObject.parseObject(result);

        return json;
    }
}
