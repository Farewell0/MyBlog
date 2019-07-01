package com.wzz.blog.dao;

import com.wzz.blog.pojo.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author wzzap
 * @date 2019/6/22
 **/
public interface TypeRepository extends JpaRepository<Type, Long> {

    /**
     * 根据分类名称查询分类
     * @param name
     * @return
     */
    Type findByName(String name);

    /**
     * 返回数据库分类中博客数从大到小排列的XX条分类
     * @param pageable
     * @return
     */
    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
