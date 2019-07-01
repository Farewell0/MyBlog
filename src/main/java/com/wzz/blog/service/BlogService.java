package com.wzz.blog.service;

import com.wzz.blog.pojo.Blog;
import com.wzz.blog.vo.BlogSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author wzzap
 * @date 2019/6/24
 **/
public interface BlogService {

    Blog getBlog(Long id);

    /**
     * 获取博客并将博客内容转换成html格式
     * @param id
     * @return
     */
    Blog getAndConvert(Long id);

    Page<Blog> listBlogs(Pageable pageable, BlogSearch blogSearch);

    /**
     * 返回包含这个标签的所有博客列表
     * @param pageable
     * @param tagId
     * @return
     */
    Page<Blog> listBlogs(Pageable pageable, Long tagId);

    /**
     * 返回所有分页的博客列表
     * @param pageable
     * @return
     */
    Page<Blog> listBlogs(Pageable pageable);

    /**
     * 根据查询条件返回标题或者内容like的博客分页列表
     * @param pageable
     * @param query
     * @return
     */
    Page<Blog> listBlogs(Pageable pageable, String query);

    List<Blog> listRecommendBlogTop(Integer size);

    /**
     * 对博客按照年份进行归档
     * @return
     */
    Map<String, List<Blog>> archiveBlogByYear();

    Long countBlog();

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);
}
