package com.qyk.Jupyter.service.impl;

import com.qyk.Jupyter.service.JupyterService;
import com.qyk.Jupyter.utils.ConfigurationFactory;
import com.qyk.Jupyter.utils.RunRemoteCommand;
import com.qyk.Jupyter.utils.StopJupyterTask;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

@Service("jupyterService")
public class JupyterServiceImpl implements JupyterService {
    private static String ip;
    private static String userName;
    private static String password;

    private static Timer timer;
    private static Map<String, TimerTask> taskMap;
    private static long stopDelay;

    static {
        ip = ConfigurationFactory.getInstance().getValue("remote_host_ip");
        userName = ConfigurationFactory.getInstance().getValue("remote_username");
        password = ConfigurationFactory.getInstance().getValue("remote_password");

        timer = new Timer();
        taskMap = new HashMap<>();
        stopDelay = Long.parseLong(ConfigurationFactory.getInstance().getValue("jupyter.stop.delay"));
    }

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
