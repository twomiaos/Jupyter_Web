package com.qyk.Jupyter.service.impl;

import com.qyk.Jupyter.service.UserService;
import com.qyk.Jupyter.utils.ConfigurationFactory;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Random;

@Repository("userService")
public class UserServiceImpl implements UserService {
    @Override
    public boolean loginCheck(String name, String password) {
        return name.equals(password);
    }

    @Override
    public boolean runJupyter(String name, Map<String, Object> map) {
        String host = ConfigurationFactory.getInstance().getValue("jupyter.host");
        String token = this.getRandomToken();
        String dir = ConfigurationFactory.getInstance().getValue("jupyter.notebook.dir") + name;
        int port = 8888 + new Random().nextInt(10000);

//        String cmd = "LANG=zh_CN nohup jupyter notebook --port=" + port + " --NotebookApp.token="
//                + token + " --NotebookApp.notebook_dir=" + dir + " --allow-root &";
        // Windows 上不能加LANG=zh_CN nohup
        String cmd = "jupyter notebook --port=" + port + " --NotebookApp.token="
                + token + " --NotebookApp.notebook_dir=" + dir + " --allow-root &";

        System.out.println(cmd);

        Runtime run = Runtime.getRuntime();
        try {
            Process process = run.exec(cmd);
            InputStream in = process.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String message;
            while((message = br.readLine()) != null) {
                sb.append(message);
            }
            System.out.println(sb);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

        //map.put("url", host + port + "/");
        map.put("dir", dir);
        map.put("token", token);
        map.put("basepath", host+port+"/");

        return true;
    }

    private String getRandomToken() {
        char[] initToken = ConfigurationFactory.getInstance().getValue("jupyter.init.token").toCharArray();
        int len = initToken.length;
        String chars = "0123456789abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();

        for (int i = 0; i < 15; i++) {
            int randomIndex = random.nextInt(len - 2);
            char randomChar = chars.charAt(random.nextInt(chars.length() - 1));
            initToken[randomIndex] = randomChar;
        }

        return new String(initToken);
    }
}
