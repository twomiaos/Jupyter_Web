package com.qyk.Jupyter.utils;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.*;

/**
 * 在 Linux 远程服务器上运行命令
 */
public class RunRemoteCommand {
    private static final Logger logger = Logger.getLogger(RunRemoteCommand.class);
    private static String DEFAULT_CHARSET = "UTF-8";
    private static Connection conn;

    /**
     * ssh2 登录
     *
     * @param ip       远程服务器 ip 地址
     * @param userName ssh2 登录用户名
     * @param password ssh2 登录密码
     * @return 是否登录成功
     */
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

    /**
     * 在远程服务器上执行命令(不获取命令运行的结果)
     *
     * @param ip          服务器 ip 地址
     * @param userName    ssh2 登录用户名
     * @param password    ssh2 登录密码
     * @param command     要执行的命令
     * @param isGetOutput 是否需要获取输出（阻塞式的命令应该设置为false）
     * @return 命令是否执行成功(" false表示不成功, true或其它表示成功 ")
     */
    public static String execute(String ip, String userName, String password, String command, boolean isGetOutput) {
        if (!login(ip, userName, password)) {
            return "false";
        }

        logger.info("execute command:" + command);

        String result = "true";
        try {
            Session session = conn.openSession();
            session.execCommand(command);

            // 防止被阻塞
            if (isGetOutput) {
                result = processStdout(session.getStdout(), DEFAULT_CHARSET);
                // 如果为得到标准输出为空，说明脚本执行出错了
                if (StringUtils.isBlank(result)) {
                    result = processStdout(session.getStderr(), DEFAULT_CHARSET);
                }
            }

            // 命令执行完毕后，及时关闭连接
            conn.close();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("远程命令执行失败!");
            return "false";
        }

        return result;
    }

    /**
     * @param in 输入流对象
     * @param charset 编码
     * @return String 以纯文本的格式返回
     */
    private static String processStdout(InputStream in, String charset) {
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
