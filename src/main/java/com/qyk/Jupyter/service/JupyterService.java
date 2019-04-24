package com.qyk.Jupyter.service;

import java.util.Map;

public interface JupyterService {
    /**
     * 获取指定 id 的 jupyter 文件信息
     * @param id 文件 id
     * @return 文件名，URL，类型，原始模板文件 id
     */
    Map<String, Object> getFileInfo(Integer id);

    /**
     * 为用户开启 jupyter 服务器实例
     * @param userInfo 用户信息(uid, roles, name)
     * @return 服务器实例是否启用成功(如果之前已经为该用户启用，则直接返回 true)
     */
    public boolean startJupyterServer(Map<String, Object> userInfo);

    /* ------------以下为Demo版本使用的-------------- */
    void keeplive(String port);

    String getJupyterList();

    boolean stopJupyterServer(String port);
    /* ------------以上为Demo版本使用的-------------- */
}
