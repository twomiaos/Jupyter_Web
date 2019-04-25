package com.qyk.Jupyter.service.impl;

import com.qyk.Jupyter.dao.ServerDao;
import com.qyk.Jupyter.model.Server;
import com.qyk.Jupyter.service.JupyterService;
import com.qyk.Jupyter.utils.ConfigurationFactory;
import com.qyk.Jupyter.utils.JwtUtils;
import com.qyk.Jupyter.utils.RunRemoteCommand;
import com.qyk.Jupyter.utils.StopJupyterTask;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("jupyterService")
public class JupyterServiceImpl implements JupyterService {
    @Autowired
    private ServerDao serverDao;

    private static final Logger logger = Logger.getLogger(JupyterServiceImpl.class);

    private static String ip;
    private static String userName;
    private static String password;
    private static String notebookDir;

    private static Timer timer;
    private static Map<String, TimerTask> taskMap;
    private static long stopDelay;

    // 读取远程jupyter服务器的信息
    static {
        ip = ConfigurationFactory.getInstance().getValue("remote_host_ip");
        userName = ConfigurationFactory.getInstance().getValue("remote_username");
        password = ConfigurationFactory.getInstance().getValue("remote_password");
        notebookDir = ConfigurationFactory.getInstance().getValue("jupyter.notebook.dir");

        timer = new Timer();
        taskMap = new HashMap<>();
        stopDelay = Long.parseLong(ConfigurationFactory.getInstance().getValue("jupyter.stop.delay"));

    }

    /**
     * 获取指定 id 的 jupyter 文件信息
     *
     * @param id 文件 id
     * @return 文件名，URL，类型，原始模板文件 id
     */
    @Override
    public Map<String, Object> getFileInfo(Integer id) {
        return null;
    }


    /**
     * 为用户开启 jupyter 服务器实例
     *
     * @param jwtToken 包含用户信息(uid, roles, name)
     * @return 服务器实例是否启用成功(如果之前已经为该用户启用 ， 则直接返回 true)
     */
    @Override
    public boolean startJupyterServer(String jwtToken) throws Exception {
        Map<String, Object> userInfo = JwtUtils.verifyToken(jwtToken);
        Integer uid = (Integer) userInfo.get("uid");
        Server server = serverDao.getByUid(uid);
        if(server == null){
            // 创建用户目录(使用用户id进行区分)
            String path = notebookDir + uid;
            if(!this.mkDir(path)){
                return false;
            }

            // 启用jupyter进程
            Integer port = 8888 + new Random().nextInt(10000);
            while(serverDao.getByPort(port) != null){
                port = 8888 + new Random().nextInt(10000);
            }
            String token = this.getRandomToken();
            String cmd = "LANG=zh_CN nohup jupyter notebook --port=" + port + " --NotebookApp.token="
                    + token + " --NotebookApp.notebook_dir=" + notebookDir + " --allow-root &";
            try {
                // 执行命令
                RunRemoteCommand.execute(ip, userName, password, cmd, false);
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("jupyter实例启用失败！");
                return false;
            }

            // 记录服务器信息到数据库
            Server newServer = new Server();
            newServer.setUid(uid);
            newServer.setPort(port);
            newServer.setCreateTime(new Date());
            newServer.setJwtToken(jwtToken);
            serverDao.insert(newServer);
        }

        return true;
    }


    /**
     * 创建文件目录
     * @param path 文件目录的路径
     * @return 是否创建成功(如果文件目录已存在，则直接返回 true)
     */
    private boolean mkDir(String path){
        String cmd = "mkdir " + path;
        String result = RunRemoteCommand.execute(ip, userName, password, cmd, true);
        if(result.equals("false")){
            return false;
        }

        if(result.length() == 0){
            logger.info("文件目录" + path + "创建成功！");
        } else {
            logger.info("文件目录" + path + "创建已经存在！");
        }

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




    /* ------------以下为Demo版本使用的-------------- */
    @Override
    public void keeplive(String port) {
        TimerTask task = taskMap.get(port);
        if(task != null){
            task.cancel();
            taskMap.remove(port);
        }

        TimerTask newTask = new StopJupyterTask(ip, userName, password, port, taskMap);
        timer.schedule(newTask, stopDelay*60000);
        taskMap.put(port, newTask);
    }
    /* ------------以上为Demo版本使用的-------------- */

    @Override
    public String getJupyterList() {
        String command = "jupyter notebook list";
        String result = RunRemoteCommand.execute(ip, userName, password, command, true);

        return result;
    }

    @Override
    public boolean stopJupyterServer(String port) {
        String command = "jupyter notebook stop " + port;
        String result = RunRemoteCommand.execute(ip, userName, password, command, true);

        return result.startsWith("Shutting");
    }
}
