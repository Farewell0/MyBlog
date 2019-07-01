package com.wzz.blog.controller;

import com.wzz.blog.pojo.Comment;
import com.wzz.blog.pojo.User;
import com.wzz.blog.service.BlogService;
import com.wzz.blog.service.CommentService;
import com.wzz.blog.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * @author wzzap
 * @date 2019/6/28
 **/
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    /**
     * 加载博客详情页面的评论
     * @param blogId 评论所属的博客id
     * @param model
     * @return 页面的评论部分（fragment）数据
     */
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "blog::commentList";
    }

    /**
     * 提交一条评论
     * @param comment
     * @return
     */
    @PostMapping("/comments")
    public String postComment(Comment comment, HttpSession session){
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        if(user != null){
//            comment.setNickname(user.getNickname());
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }else{
            comment.setAvatar(avatar);
            comment.setAdminComment(false);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + comment.getBlog().getId();
    }

}
