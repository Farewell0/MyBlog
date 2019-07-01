package com.wzz.blog.dao;

import com.wzz.blog.pojo.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wzzap
 * @date 2019/6/28
 **/
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * 根据博客id查询评论，并将其排序
     * @param blogId
     * @param sort
     * @return
     */
    List<Comment> findByBlogId(Long blogId, Sort sort);
}
