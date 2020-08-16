package com.ce.service;

import com.ce.domain.Permission;

import java.util.List;

public interface PermissionService {


    List<Permission> findAll();

    void save(Permission permission);
}
