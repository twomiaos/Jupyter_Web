package com.qyk.Jupyter.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 正在运行的jupyter服务器实例
 */
public class Server {
    private Integer uid;
    private Integer port;
    private String token;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String jwtToken;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

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

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Override
    public String toString() {
        return "Server{" +
                "uid=" + uid +
                ", port=" + port +
                ", token='" + token + '\'' +
                ", createTime=" + createTime +
                ", jwtToken='" + jwtToken + '\'' +
                '}';
    }
}
