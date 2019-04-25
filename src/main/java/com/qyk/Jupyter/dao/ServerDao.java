package com.qyk.Jupyter.dao;

import com.qyk.Jupyter.model.Server;

import java.util.List;

public interface ServerDao {
    /**
     * 获取所有正在运行的服务器信息
     * @return 服务器列表
     */
    List<Server> getAll();

    /**
     * 获取指定用户的jupyter服务器信息
     * @param uid 用户id
     * @return jupyter服务器信息
     */
    Server getByUid(Integer uid);

    /**
     * 获取指定端口的服务器信息
     * @param port jupyter服务器运行的端口号（8888~18888）
     * @return 服务器信息
     */
    Server getByPort(Integer port);

    /**
     * 删除指定端口的服务器信息
     * @param port jupyter服务器运行的端口号（8888~18888）
     */
    void deleteByPort(Integer port);

    /**
     * 删除指定token的服务器信息
     * @param token jupyter服务器与用户交互的唯一标识(长度为46的字符串)
     */
    void deleteByToken(String token);

    void deleteByUid(Integer uid);

    /**
     * 新增一条正在运行的jupyter服务器信息
     * @param server jupyter服务器(所有字段必须为非空)
     */
    void insert(Server server);
}
