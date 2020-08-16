package com.ce.controller;

import com.ce.domain.Role;
import com.ce.domain.UserInfo;
import com.ce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/findAll.do")
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView findAll() {
        ModelAndView modelAndView = new ModelAndView("user-list");
        List<UserInfo> users = userService.findAll();
        modelAndView.addObject("userList", users);
        return modelAndView;
    }

    @RequestMapping("/save.do")
    // @PreAuthorize("authentication.principal.username=='tom'")
    public String save(UserInfo userInfo) {
        userService.save(userInfo);
        return "redirect:findAll.do";
    }

    @RequestMapping("/findById.do")
    public ModelAndView findById(String id) {
        ModelAndView modelAndView = new ModelAndView("user-show");
        UserInfo userInfo = userService.findById(id);
        modelAndView.addObject("user", userInfo);
        return modelAndView;
    }

    /**
     * 查询用户以及用户可以添加的角色
     */
    @RequestMapping("/findUserByIdAndAllRole.do")
    public ModelAndView findUserByIdAndAllRole(@RequestParam(name = "id", required = true) String id) {
        ModelAndView modelAndView = new ModelAndView("user-role-add");
        // 1.根据用户id查询用户
        UserInfo userInfo = userService.findById(id);
        // 2.根据用户id查询可以添加的角色
        List<Role> otherRoles = userService.findOtherRoles(id);
        modelAndView.addObject("user", userInfo);
        modelAndView.addObject("roleList", otherRoles);
        return modelAndView;
    }

    /**
     * 添加角色
     */
    @RequestMapping("/addRoleToUser.do")
    public String addRoleToUser(@RequestParam(name = "userId", required = true) String userId, @RequestParam(name = "ids", required = true) String[] roleIds) {
        userService.addRoleToUser(userId, roleIds);
        return "redirect:findAll.do";
    }
}
