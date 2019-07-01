package com.wzz.blog.controller.admin;

import com.wzz.blog.pojo.Tag;
import com.wzz.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author wzzap
 * @date 2019/6/23
 **/
@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String listTags(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC)
                           Pageable pageable, Model model){
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String toAddTag(Model model){
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    @GetMapping("/tags/{id}/input")
    public String toEditTag(@PathVariable Long id, Model model){
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags-input";
    }

    @PostMapping("/tag")
    public String addTag(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        if(tagService.getTagByName(tag.getName()) != null){
            result.rejectValue("name", "nameError", "不能添加重复的标签");
        }
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        Tag t = tagService.saveTag(tag);
        if(t == null){
            attributes.addFlashAttribute("message", "添加标签失败");
        }else{
            attributes.addFlashAttribute("message", "添加标签成功");
        }
        return "redirect:/admin/tags";
    }
    @PostMapping("/tag/{id}")
    public String editTag(@Valid Tag tag, BindingResult result, @PathVariable Long id, RedirectAttributes attributes){
        if(tagService.getTagByName(tag.getName()) != null){
            result.rejectValue("name", "nameError", "不能添加重复的标签");
        }
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        Tag t = tagService.updateTag(id, tag);
        if(t == null){
            attributes.addFlashAttribute("message", "修改标签失败");
        }else{
            attributes.addFlashAttribute("message", "修改标签成功");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        tagService.removeTag(id);
        attributes.addFlashAttribute("message", "删除标签成功");
        return "redirect:/admin/tags";
    }
}
