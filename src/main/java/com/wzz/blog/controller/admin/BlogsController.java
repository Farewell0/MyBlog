package com.wzz.blog.controller.admin;

import com.wzz.blog.pojo.Blog;
import com.wzz.blog.pojo.User;
import com.wzz.blog.service.BlogService;
import com.wzz.blog.service.TagService;
import com.wzz.blog.service.TypeService;
import com.wzz.blog.utils.Constant;
import com.wzz.blog.vo.BlogSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


/**
 * @author wzzap
 * @date 2019/6/22
 **/
@Controller
@RequestMapping("/admin")
public class BlogsController {

    private static final String INPUT = "admin/blog-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    /**
     * 博客分页列表
     * @param pageable
     * @param blogSearch
     * @param model
     * @return
     */
    @GetMapping("/blogs")
    public String list(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                               Pageable pageable, BlogSearch blogSearch, Model model){
        model.addAttribute("page", blogService.listBlogs(pageable, blogSearch));
        model.addAttribute("types", typeService.listType());
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                               Pageable pageable, BlogSearch blog, Model model){
        model.addAttribute("page", blogService.listBlogs(pageable, blog));
        // 返回blogs页面中定义的fragment片段，即那个需要局部渲染的表格
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String input(Model model){
        setTypeAndTag(model);
        model.addAttribute("blog", new Blog());
        return INPUT;
    }

    private void setTypeAndTag(Model model){
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
    }

    @GetMapping("/blogs/{id}/input")
    public String editBlog(@PathVariable Long id, Model model){
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog", blog);
        return INPUT;
    }


    @PostMapping("/blogs")
    public String postBlog(Blog blog, RedirectAttributes attributes, HttpSession session){
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setUser((User) session.getAttribute(Constant.CURRENT_USER));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        if(blog.getId() == null){
            b = blogService.saveBlog(blog);
        }else{
            b = blogService.updateBlog(blog.getId(), blog);
        }
        if(b == null){
            attributes.addFlashAttribute("message", "操作失败");
        }else{
            attributes.addFlashAttribute("message", "操作成功");
        }
        return REDIRECT_LIST;
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return REDIRECT_LIST;
    }
}
