package com.wzz.blog.dao;

import com.wzz.blog.pojo.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wzzap
 * @date 2019/6/24
 **/
public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {

    /**
     * 查询前几个推荐的博客列表
     * @param pageable
     * @return
     */
    @Query("select b from Blog b where b.canRecommend = true")
    List<Blog> findBlogTop(Pageable pageable);

    /**
     * 根据博客标题或者博客内容进行模糊查询
     * @param query 查询条件
     * @param pageable 分页信息: 需要获取的数量，排序方式
     * @return 分页后的博客列表
     */
    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> fingByQuery(String query, Pageable pageable);

    /**
     * 更新博客浏览数（+1)
     * @param id
     * @return
     */
    @Modifying
    @Query("update Blog b set b.views = b.views + 1 where b.id = ?1")
    int updateViews(Long id);

    /**
     * 返回倒序排序的所有博客年份列表
     * @return
     */
    @Query("select function('date_format', b.updateTime, '%Y') as year from Blog b group by function('date_format', b.updateTime, '%Y') order by year")
    List<String> findGroupYear();

    /**
     * 查询指定年份的博客
     * @param year
     * @return
     */
    @Query("select b from Blog b where function('date_format', b.updateTime, '%Y') = ?1")
    List<Blog> findByYear(String year);

    /**
     * 返回已发布的所有博客数量
     * @return
     */
    @Query("select count(b) from Blog b where b.published = 1")
    Long countPublishedBlogs();
}
