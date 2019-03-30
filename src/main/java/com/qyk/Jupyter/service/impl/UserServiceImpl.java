package com.qyk.Jupyter.service.impl;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.qyk.Jupyter.service.UserService;
import com.qyk.Jupyter.utils.ConfigurationFactory;
import com.qyk.Jupyter.utils.RemoteConnect;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.io.*;
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

        System.out.println(cmd);

        try {
            this.connectLinux(remoteIp, username, password, cmd);
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

    /**
     *
     * @Title: ConnectLinux
     * @Description: 通过用户名和密码关联linux服务器
     * @return
     * @return String
     * @throws
     */
    private boolean connectLinux(String ip,String userName,String password,String commandStr) {

        logger.info("ConnectLinuxCommand  scpGet===" + "ip:" + ip + "  userName:" + userName + "  commandStr:"
                + commandStr);

        String returnStr = "";
        boolean result = true;

        RemoteConnect remoteConnect = new RemoteConnect();
        remoteConnect.setIp(ip);
        remoteConnect.setUserName(userName);
        remoteConnect.setPassword(password);
        try {
            if (login(remoteConnect)) {
                returnStr = execute(commandStr);
                System.out.println(returnStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (StringUtils.isBlank(returnStr)) {
            result = false;
        }
        return result;
    }

    /**
     *
     * @Title: login
     * @Description: 用户名密码方式  远程登录linux服务器
     * @return: Boolean
     * @throws
     */
    private Boolean login(RemoteConnect remoteConnect) {
        boolean flag = false;
        try {
            conn = new Connection(remoteConnect.getIp());
            conn.connect();// 连接
            flag = conn.authenticateWithPassword(remoteConnect.getUserName(), remoteConnect.getPassword());// 认证
            if (flag) {
                logger.info("认证成功！");
            } else {
                logger.info("认证失败！");
                conn.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     *
     * @Title: execute
     * @Description: 远程执行shll脚本或者命令
     * @param cmd 脚本命令
     * @return: result 命令执行完毕返回结果
     * @throws
     */
    private String execute(String cmd){
        String result = "";
        try {
            Session session = conn.openSession();// 打开一个会话
            session.execCommand(cmd);// 执行命令
            // 防止被阻塞
            // result = processStdout(session.getStdout(), DEFAULTCHARTSET);
            // 如果为得到标准输出为空，说明脚本执行出错了
//            if (StringUtils.isBlank(result)) {
//                result = processStdout(session.getStderr(), DEFAULTCHARTSET);
//            }
            conn.close();
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * @Title: executeSuccess
     * @Description: 远程执行shell脚本或者命令
     * @param: cmd 脚本或者命令
     * @return String 命令执行成功后返回的结果值，如果命令执行失败，返回空字符串，不是null
     * @throws
     */
    private String executeSuccess(String cmd){
        String result = "";
        try {
            Session session = conn.openSession();// 打开一个会话
            session.execCommand(cmd);// 执行命令
            result = processStdout(session.getStdout(), DEFAULTCHARTSET);
            conn.close();
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     *
     * @Title: processStdout
     * @Description: 解析脚本执行的返回结果
     * @param in 输入流对象
     * @param charset 编码
     * @return String 以纯文本的格式返回
     * @throws
     */
    private String processStdout(InputStream in, String charset){
        InputStream stdout = new StreamGobbler(in);
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout, charset));
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line + "\n");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}


