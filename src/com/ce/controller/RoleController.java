package com.ce.controller;

import com.ce.dao.RoleDao;
import com.ce.domain.Permission;
import com.ce.domain.Role;
import com.ce.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll() {
        ModelAndView modelAndView = new ModelAndView("role-list");
        List<Role> role = roleService.findAll();
        modelAndView.addObject("roleList", role);
        return modelAndView;
    }

    @RequestMapping("/save.do")
    public String save(Role role) {
        roleService.save(role);
        return "redirect:findAll.do";
    }

    @RequestMapping("/findById.do")
    public String findById(Model model, @RequestParam(name = "id", required = true) String roleId) {
        Role role = roleService.findById(roleId);
        model.addAttribute("role", role);
        return "role-show";
    }

    /**
     * 根据roleId查询role，并查询可以添加的权限
     */
    @RequestMapping("/findRoleByIdAndAllPermission.do")
    public ModelAndView findRoleByIdAndAllPermission(@RequestParam(name = "id", required = true) String roleId) {
        ModelAndView modelAndView = new ModelAndView("role-permission-add");
        // 根据roleId查询role
        Role role = roleService.findById(roleId);
        // 根据roleId查询可以添加的权限
        List<Permission> otherPermissions = roleService.findOtherPermissions(roleId);
        modelAndView.addObject("role", role);
        modelAndView.addObject("permissionList", otherPermissions);
        return modelAndView;
    }

    /**
     * 给角色添加权限
     */
    @RequestMapping("/addPermissionToRole.do")
    public String addPermissionToRole(@RequestParam(name = "roleId", required = true) String roleId, @RequestParam(name = "ids", required = true) String[] permissionIds) {
        roleService.addPermissionToRole(roleId, permissionIds);
        return "redirect:findAll.do";
    }
}
