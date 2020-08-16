package com.ce.controller;

import com.ce.domain.Product;
import com.ce.service.ProductService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;

    /**
     * 查询所有产品
     */
    @RequestMapping("/findAll.do")
    // @RolesAllowed("ADMIN")
    public ModelAndView findAll(@RequestParam(name = "page", required = true, defaultValue = "1") Integer page, @RequestParam(name = "size", required = true, defaultValue = "4") Integer size) {
        ModelAndView mv = new ModelAndView("product-list");
        List<Product> products = service.findAll(page, size);
        PageInfo<Product> pageInfo = new PageInfo<>(products);
        mv.addObject("pageInfo", pageInfo);
        return mv;
    }

    /**
     * 添加产品
     */
    @RequestMapping("/save.do")
    public String save(Product product) {
        service.save(product);
        return "redirect:findAll.do";
    }
}
