package com.qyk.Jupyter.service;

import com.alibaba.fastjson.JSONObject;

public interface FileService {
    /**
     * 根据文件 id 获取文件相关信息
     * @param fileId 文件 id
     * @return 文件信息，格式为
     * {
     *   "create_id": 0,
     *   "create_time": {},
     *   "fmt_size": "string",
     *   "id": 0,
     *   "name": "string",
     *   "orig_id": 0,
     *   "size": 0,
     *   "stype": 0,
     *   "update_time": {},
     *   "url": "string"
     * }
     */
    JSONObject getFileInfo(Integer fileId);
}
