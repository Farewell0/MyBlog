package com.wzz.blog.controller.admin;

import com.wzz.blog.pojo.Type;
import com.wzz.blog.service.TypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @date 2019/6/22
 **/
@Controller
@RequestMapping("/admin")
public class TypeController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){
        // 默认分页数量10条，按照id倒序排列
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String typesAdd(Model model){
        model.addAttribute("type", new Type());
        return "admin/type-input";
    }

    @PostMapping("/type")
    public String postType(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        if(typeService.getTypeByName(type.getName()) != null){
            result.rejectValue("name", "nameError", "不能添加重复的分类");
        }
        if(result.hasErrors()) {
            return "admin/type-input";
        }
        Type t = typeService.saveType(type);
        if(t == null){
            attributes.addFlashAttribute("message", "新增失败");
        }else{
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/type/{id}")
    public String editType(@Valid Type type, BindingResult result, @PathVariable Long id, RedirectAttributes attributes){
        if(typeService.getTypeByName(type.getName()) != null){
            result.rejectValue("name", "nameError", "不能添加重复的分类");
        }
        if(result.hasErrors()) {
            return "admin/type-input";
        }
        Type t = typeService.updateType(id, type);
        logger.info("========修改分类========");
        if(t == null){
            attributes.addFlashAttribute("message", "修改失败");
        }else{
            attributes.addFlashAttribute("message", "修改成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type", typeService.getType(id));
        return "admin/type-input";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        typeService.removeType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }
}
