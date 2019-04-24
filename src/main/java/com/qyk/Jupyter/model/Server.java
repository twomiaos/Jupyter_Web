package com.qyk.Jupyter.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 正在运行的jupyter服务器实例
 */
public class Server {
    private Integer port;
    private String token;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Server{" +
                "port=" + port +
                ", token='" + token + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
