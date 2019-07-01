package com.wzz.blog.controller;

import com.wzz.blog.pojo.Type;
import com.wzz.blog.service.BlogService;
import com.wzz.blog.service.TypeService;
import com.wzz.blog.utils.PropertiesUtil;
import com.wzz.blog.vo.BlogSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author wzzap
 * @date 2019/6/28
 **/
@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PathVariable Long id, Model model,
                        @PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                        Pageable pageable){
        List<Type> typeList = typeService.listTypeTop(
                Integer.valueOf(PropertiesUtil.getProperty("types_count", "10000")));
        // 从导航点击分类传值-1, 默认选择第一条分类
        if(id == -1){
            id = typeList.get((0)).getId();
        }
        model.addAttribute("types", typeList);
        BlogSearch blogSearch = new BlogSearch();
        blogSearch.setTypeId(id);
        model.addAttribute("page", blogService.listBlogs(pageable, blogSearch));
        model.addAttribute("activeTypeId", id);
        return "types";
    }
}
