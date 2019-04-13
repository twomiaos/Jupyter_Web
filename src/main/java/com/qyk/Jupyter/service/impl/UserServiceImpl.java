package com.qyk.Jupyter.service.impl;

import ch.ethz.ssh2.Connection;
import com.qyk.Jupyter.service.UserService;
import com.qyk.Jupyter.utils.ConfigurationFactory;
import com.qyk.Jupyter.utils.RunRemoteCommand;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

@Repository("userService")
public class UserServiceImpl implements UserService {
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
    private static String DEFAULTCHARTSET = "UTF-8";
    private static Connection conn;

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
//        try {
//            Process process = run.exec(cmd);
//            InputStream in = process.getInputStream();
//            InputStreamReader reader = new InputStreamReader(in);
//            BufferedReader br = new BufferedReader(reader);
//            StringBuffer sb = new StringBuffer();
//            String message;
//            while((message = br.readLine()) != null) {
//                sb.append(message);
//            }
//            System.out.println(sb);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return false;
//        }
        try {
            Process process = run.exec("cmd /c " + cmd);
//            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
//            String line = null;
//            StringBuilder build = new StringBuilder();
//            while ((line = br.readLine()) != null) {
//                System.out.println(line);
//                build.append(line);
//            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


        //map.put("url", host + port + "/");
        map.put("dir", dir);
        map.put("token", token);
        map.put("basepath", host + port + "/");

        return true;
    }

    @Override
    public boolean runRemoteJupyter(String name, Map<String, Object> map) {
        String remoteIp = ConfigurationFactory.getInstance().getValue("remote_host_ip");
        String username = ConfigurationFactory.getInstance().getValue("remote_username");
        String password = ConfigurationFactory.getInstance().getValue("remote_password");
        String host = ConfigurationFactory.getInstance().getValue("jupyter.host");
        String token = this.getRandomToken();
        String dir = ConfigurationFactory.getInstance().getValue("jupyter.notebook.dir") + name;
        int port = 8888 + new Random().nextInt(10000);

        String cmd = "LANG=zh_CN nohup jupyter notebook --port=" + port + " --NotebookApp.token="
                + token + " --NotebookApp.notebook_dir=" + dir + " --allow-root &";
        try {
            // 执行命令
            RunRemoteCommand.execute(remoteIp, username, password, cmd, false);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        //map.put("url", host + port + "/");
        map.put("dir", dir);
        map.put("token", token);
        map.put("basepath", host + port + "/");

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


