package com.qyk.Jupyter.service;

import java.util.Map;

public interface UserService {
    boolean loginCheck(String name, String password);

    boolean runJupyter(String name, Map<String, Object> map);

    boolean runRemoteJupyter(String name, Map<String, Object> map);
}
