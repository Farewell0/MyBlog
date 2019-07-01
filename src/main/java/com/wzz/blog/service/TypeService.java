package com.wzz.blog.service;

import com.wzz.blog.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author wzzap
 * @date 2019/6/22
 **/
public interface TypeService {

    /**
     * 新增分类
     * @param type
     * @return
     */
    Type saveType(Type type);

    /**
     * 根据id获取分类
     * @param id
     * @return
     */
    Type getType(Long id);

    /**
     * 根据名称查询分类
     * @param name
     * @return
     */
    Type getTypeByName(String name);

    /**
     * 获取分页的所有分类
     * @param pageable
     * @return
     */
    Page<Type> listType(Pageable pageable);

    /**
     * 修改分类
     * @param id
     * @param type
     * @return
     */
    Type updateType(Long id, Type type);

    /**
     * 根据id删除分类
     * @param id
     */
    void removeType(Long id);

    /**
     * 返回所有类别
     * @return
     */
    List<Type> listType();

    List<Type> listTypeTop(Integer size);
}
