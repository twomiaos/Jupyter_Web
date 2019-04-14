package com.qyk.Jupyter.service;

public interface JupyterService {
    void keeplive(String port);

    String getJupyterList();

    boolean stopJupyterServer(String port);
}
