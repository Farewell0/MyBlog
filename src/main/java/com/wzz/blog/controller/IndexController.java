package com.wzz.blog.controller;

import com.wzz.blog.service.BlogService;
import com.wzz.blog.service.TagService;
import com.wzz.blog.service.TypeService;
import com.wzz.blog.utils.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wzzap
 * @date 2019/6/20
 **/
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){
        model.addAttribute("page", blogService.listBlogs(pageable));
        model.addAttribute("types", typeService.listTypeTop(Integer.valueOf(PropertiesUtil.getProperty("type_blog.count", "6"))));
        model.addAttribute("tags", tagService.listTagTop(Integer.valueOf(PropertiesUtil.getProperty("tag_blog.count", "10"))));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(Integer.valueOf(PropertiesUtil.getProperty("recommend_blog.count", "8"))));
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                     Pageable pageable, Model model, @RequestParam String query){
        model.addAttribute("page", blogService.listBlogs(pageable, "%" + query + "%"));
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "blog";
    }

    @GetMapping("/footer/newblog")
    public String newblogs(Model model) {
        model.addAttribute("newblogs",
                blogService.listRecommendBlogTop(Integer.valueOf(PropertiesUtil.getProperty("new_blog.count", "3"))));
        return "_fragments :: newblogList";
    }

}
