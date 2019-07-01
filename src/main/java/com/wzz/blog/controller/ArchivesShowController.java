package com.wzz.blog.controller;

import com.wzz.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author wzzap
 * @date 2019/6/29
 **/
@Controller
public class ArchivesShowController {
    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model){
        model.addAttribute("archiveMap", blogService.archiveBlogByYear());
        model.addAttribute("blogCount", blogService.countBlog());
        return "archives";
    }
}
