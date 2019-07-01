package com.wzz.blog.service;

import com.wzz.blog.pojo.Comment;

import java.util.List;

/**
 * @author wzzap
 * @date 2019/6/28
 **/
public interface CommentService {

    /**
     * 根据博客id返回评论列表
     * @param blogId
     * @return
     */
    List<Comment> listCommentByBlogId(Long blogId);

    /**
     * 提交一条评论
     * @param comment
     * @return
     */
    Comment saveComment(Comment comment);
}
