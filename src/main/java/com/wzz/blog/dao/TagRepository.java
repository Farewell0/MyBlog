package com.wzz.blog.dao;

import com.wzz.blog.pojo.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author wzzap
 * @date 2019/6/23
 **/
public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findByName(String name);

    List<Tag> findByIdIn(List<Long> ids);

    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}
