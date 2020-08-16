package com.ce.controller;

import com.ce.domain.SysLog;
import com.ce.service.SysLogService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/sysLog")
public class SysLogController {
    @Autowired
    private SysLogService sysLogService;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name = "page", required = true, defaultValue = "1") Integer page, @RequestParam(name = "size", required = true, defaultValue = "10") Integer size) {
        ModelAndView modelAndView = new ModelAndView("syslog-list");
        List<SysLog> sysLogList = sysLogService.findAll(page, size);
        PageInfo pageInfo = new PageInfo<>(sysLogList);
        modelAndView.addObject("pageInfo", pageInfo);
        return modelAndView;
    }
}
