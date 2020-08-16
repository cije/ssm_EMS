package com.ce.service;

import com.ce.domain.SysLog;

import java.util.List;

public interface SysLogService {
    /**
     * 保存
     */
    void save(SysLog sysLog);

    List<SysLog> findAll(Integer page, Integer size);
}
