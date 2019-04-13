package com.qyk.Jupyter.utils;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import org.apache.log4j.Logger;

import java.io.*;

/**
 * 在 Linux 远程服务器上运行命令
 */
public class RunRemoteCommand {
    private static final Logger logger = Logger.getLogger(RunRemoteCommand.class);
    private static String DEFAULT_CHARSET = "UTF-8";
    private static Connection conn;

    private static Boolean login(String ip, String userName, String password) {
        boolean flag = false;
        try {
            conn = new Connection(ip);
            conn.connect();// 连接
            flag = conn.authenticateWithPassword(userName, password);// 认证
            if (flag) {
                logger.info("ssh2连接成功！");
            } else {
                logger.info("ssh2连接失败！");
                conn.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static boolean execute(String ip, String userName, String password, String command){
        if(!login(ip, userName, password)){
            return false;
        }

        logger.info("execute command:" + command);

        String result = "";
        try {
            Session session = conn.openSession();
            session.execCommand(command);

            // 防止被阻塞
            // result = processStdout(session.getStdout(), DEFAULTCHARTSET);
            // 如果为得到标准输出为空，说明脚本执行出错了
//            if (StringUtils.isBlank(result)) {
//                result = processStdout(session.getStderr(), DEFAULT_CHARSET);
//            }

            // 命令执行完毕后，及时关闭连接
            conn.close();
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
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
