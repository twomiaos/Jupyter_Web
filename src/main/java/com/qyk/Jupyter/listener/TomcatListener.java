package com.qyk.Jupyter.listener;

import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by Administrator on 2017/10/23.
 * 监听Tomcat的生命周期，Web应用启动和关闭时执行
 */
public class TomcatListener implements ServletContextListener
{
    private static Logger logger = Logger.getLogger(TomcatListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("Tomcat server开始启动.....................");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        logger.info("Tomcat server正在关闭.....................");
    }
}
