package com.qyk.Jupyter.utils;

import java.util.Map;
import java.util.TimerTask;

public class StopJupyterTask extends TimerTask {
    private String ip;
    private String userName;
    private String password;
    private String port;
    private  Map<String, TimerTask> taskMap;

    public StopJupyterTask() {
    }

    public StopJupyterTask(String ip, String userName, String password, String port, Map<String, TimerTask> taskMap) {
        this.ip = ip;
        this.userName = userName;
        this.password = password;
        this.port = port;
        this.taskMap = taskMap;
    }

    @Override
    public void run() {
        String command = "jupyter notebook stop " + port;
        String result = RunRemoteCommand.execute(ip, userName, password, command, true);
        taskMap.remove(port);
    }
}
